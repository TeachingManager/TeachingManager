package com.TeachingManager.TeachingManager.Service.Attend;

import com.TeachingManager.TeachingManager.DTO.Attend.AttendInfo;
import com.TeachingManager.TeachingManager.DTO.Attend.MonthLectureAttendResponse;
import com.TeachingManager.TeachingManager.DTO.Attend.MonthStudentAttendResponse;
import com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.LectureMonthAttendanceRecord;
import com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.StudentsMonthAttendRecord;
import com.TeachingManager.TeachingManager.DTO.Attend.Request.UpdateAttendListRequest;
import com.TeachingManager.TeachingManager.DTO.Attend.SimpleAttendInfo;
import com.TeachingManager.TeachingManager.Repository.Attend.AttendRepository;
import com.TeachingManager.TeachingManager.Repository.Lecture.LectureRepository;
import com.TeachingManager.TeachingManager.Repository.Schedule.ScheduleRepository;
import com.TeachingManager.TeachingManager.Repository.Student.StudentRepository;
import com.TeachingManager.TeachingManager.domain.Attend;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Schedule;
import com.TeachingManager.TeachingManager.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttendServiceImpl implements AttendService{

    private final AttendRepository attendRepo;
    private final StudentRepository studentRepo;
    private final ScheduleRepository scheduleRepo;

    //////////////////////////////////////////////////////////
    ///                       추가                           //
    //////////////////////////////////////////////////////////
    @Override
    @Transactional
    public AttendInfo createSingleAttend(CustomUser user, Long schedule_id, Long student_id) {
        Schedule schedule = scheduleRepo.searchById(user.getPk(), schedule_id).orElseThrow(() -> new RuntimeException("출석 튜플 생성시 잘못된 일정 참조시도"));
        Student student = studentRepo.findById(user.getPk(), student_id).orElseThrow(()->new RuntimeException("출석 튜플 생성시 잘못된 학생 참조시도"));

        return new AttendInfo(attendRepo.save(Attend.builder()
                        .schedule(schedule)
                        .student(student)
                        .memo("없음")
                        .attendance((byte)0)
                .build()));

    }


    //////////////////////////////////////////////////////////
    ///                       조회                           //
    //////////////////////////////////////////////////////////

    // 출석 아이디로 개별 조회하는 서비스 함수
    @Override
    public AttendInfo searchById(CustomUser user, Long attend_id) {
        Optional<Attend> attend = attendRepo.searchById(user.getPk(), attend_id);
        return attend.map(AttendInfo::new).orElse(null);
    }


    // 특정 달의 특정 강의의 학생출석리스트
    @Override
    public List<MonthLectureAttendResponse> findMonthlyLectureAttendance(CustomUser user, Long lecture_id, LocalDate date_info) {
        List<LectureMonthAttendanceRecord> attendanceList = attendRepo.searchMonthlyAttendanceByLectureId(user.getPk(), lecture_id, date_info);
        List<MonthLectureAttendResponse> resultList = new ArrayList<MonthLectureAttendResponse>();

        MonthLectureAttendResponse tempResult = null;
        List<SimpleAttendInfo> tempAttendInfoList = new ArrayList<SimpleAttendInfo>();


        Iterator<LectureMonthAttendanceRecord> iterator = attendanceList.iterator();
        Long CurrentStudentId = 0L;

        while(iterator.hasNext()) {
            LectureMonthAttendanceRecord nextRecord = iterator.next();
            Long nextStudent_id = nextRecord.getStudent_id();

            if(!Objects.equals(nextStudent_id, CurrentStudentId)){
                CurrentStudentId = nextStudent_id;

                if(tempResult != null){
                    tempResult.setAttendInfoList(new ArrayList<>(tempAttendInfoList));
                    resultList.add(tempResult);

                    tempAttendInfoList.clear();
                }

                tempResult = new MonthLectureAttendResponse();
                tempResult.setLecture_id(lecture_id);
                tempResult.setStudent_id(nextRecord.getStudent_id());
                tempResult.setStudent_name(nextRecord.getStudent_name());
            }
            tempAttendInfoList.add(new SimpleAttendInfo(nextRecord.getAttend_id(), nextRecord.getAttendance(), nextRecord.getStart_date()));

        }

        if (tempResult != null) {
            tempResult.setAttendInfoList(tempAttendInfoList);
            resultList.add(tempResult);
        }

        return resultList;
    }


    // 특정 달의 특정 학생의 강의출석리스트
    @Override
    public List<MonthStudentAttendResponse> findMonthlyStudentAttendance(CustomUser user, Long student_id, LocalDate date_info) {
        List<StudentsMonthAttendRecord> attendanceList = attendRepo.searchMonthlyAttendanceByStudentId(user.getPk(), student_id, date_info);
        List<MonthStudentAttendResponse> resultList = new ArrayList<MonthStudentAttendResponse>();

        MonthStudentAttendResponse tempResult = null;
        List<SimpleAttendInfo> tempAttendInfoList = new ArrayList<SimpleAttendInfo>();
        
        Iterator<StudentsMonthAttendRecord> iterator = attendanceList.iterator();
        Long CurrentLectureId = 0L;

        while(iterator.hasNext()) {
            StudentsMonthAttendRecord nextRecord = iterator.next();
            Long nextStudent_id = nextRecord.getLecture_id();

            if(!Objects.equals(nextStudent_id, CurrentLectureId)){
                CurrentLectureId = nextStudent_id;

                if(tempResult != null){
                    tempResult.setAttendInfoList(new ArrayList<>(tempAttendInfoList));
                    resultList.add(tempResult);

                    tempAttendInfoList.clear();
                }

                tempResult = new MonthStudentAttendResponse();
                tempResult.setStudent_id(student_id);
                tempResult.setLecture_id(nextRecord.getLecture_id());
                tempResult.setLecture_name(nextRecord.getLecture_name());
            }
            tempAttendInfoList.add(new SimpleAttendInfo(nextRecord.getAttend_id(), nextRecord.getAttendance(), nextRecord.getStart_date()));

        }

        if (tempResult != null) {
            tempResult.setAttendInfoList(tempAttendInfoList);
            resultList.add(tempResult);
        }

        return resultList;
    }

    //////////////////////////////////////////////////////////
    ///               업데이트(출석 정보 입력)                  //
    //////////////////////////////////////////////////////////

    // 전달된 값들 반영
    @Override
    @Transactional
    public String updateAttends(CustomUser user, UpdateAttendListRequest request) {
        Long institute_id = user.getPk();
        Optional<Attend> attend = Optional.empty();
        for (Map.Entry<Long, Byte> entry : request.getAttendList().entrySet()) {
            Long attend_id = entry.getKey();
            Byte attendance = entry.getValue();
            attend = attendRepo.searchById(institute_id, attend_id);
            if(attend.isPresent()){
                attend.get().setAttendance(attendance);
                attendRepo.save(attend.get());
            }
        }
        return "출석 반영 완료";
    }


    //////////////////////////////////////////////////////////
    ///                       삭제                           //
    //////////////////////////////////////////////////////////
    // 단 한개의 출석 삭제 서비스
    @Override
    @Transactional
    public String deleteSingleAttend(CustomUser user, Long attend_id) {
        return attendRepo.delete(user.getPk(), attend_id);
    }
}
