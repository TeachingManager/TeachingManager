package com.TeachingManager.TeachingManager.Service.Enroll;

import com.TeachingManager.TeachingManager.DTO.Enroll.Request.EnrollLectureRequest;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.*;
import com.TeachingManager.TeachingManager.DTO.Fee.EnrollFeeResponse;
import com.TeachingManager.TeachingManager.DTO.Fee.EnrollYearFeeResponse;
import com.TeachingManager.TeachingManager.Repository.Attend.AttendRepository;
import com.TeachingManager.TeachingManager.Repository.Enroll.EnrollRepository;
import com.TeachingManager.TeachingManager.Repository.Fee.FeeRepository;
import com.TeachingManager.TeachingManager.Repository.Lecture.LectureRepository;
import com.TeachingManager.TeachingManager.Repository.Schedule.ScheduleRepository;
import com.TeachingManager.TeachingManager.Repository.Student.StudentRepository;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService{

    private final EnrollRepository enrollRepo;
    private final LectureRepository lectureRepo;
    private final StudentRepository studentRepo;
    private final ScheduleRepository scheduleRepo;
    private final AttendRepository attendRepo;
    private final InstituteRepository instituteRepo;
    private final FeeRepository feeRepo;

    //////////////////////////////////////////////////////////
    ///                       조회                           //
    //////////////////////////////////////////////////////////
    @Override
    public List<EnrolledStudentsResponse> findMonthlyEnrolledStudents(CustomUser user, Long lecture_id, Short year, Short month) {
        return enrollRepo.findEnrolledStudentsByDate(user.getPk(), lecture_id, year, month);
    }

    @Override
    public List<EnrolledLecturesResponse> findMonthlyEnrolledLectures(CustomUser user, Short year, Short month) {
        return enrollRepo.findEnrolledLecturesByDate(user.getPk(), year, month);
    }

    @Override
    public List<NotEnrolledLecturesResponse> findMonthlyNotEnrolledLectures(CustomUser user, Short year, Short month) {
        return enrollRepo.findNotEnrolledLecturesByDate(user.getPk(), year, month);
    }



    //////////////////////////////////////////////////////////
    ///                       추가                           //
    //////////////////////////////////////////////////////////
    
    // 강의 등록
    // 3가지 로직이 필요하다.
    // 우선 request 안의 학생들 정보리스트와 lecture_id 를 이용하여 수강 저장 (이번달이어야함. 갑자기 지난달 으로는 추가 불가..)
    // 강의를 이번달 일정에 추가 (역시 이번달이여야함)
    // 일정들을 생성하는 과정 중간에 해당 일정들과 해당 학생들의 출석 튜플 생성해야함.
    // 중요한 것은 이미 해당 강의가 수강 테이블에 존재하는지 체크하고 없을 때에만 작동해야함.
    @Override
    @Transactional
    public EnrolledLecturesResponse registerEnroll(CustomUser user, Long lecture_id, EnrollLectureRequest request, Short year, Short monthShort) {

        List<EnrolledLecturesResponse> enrolledLecturesResponseList = enrollRepo.findEnrolledLecturesByDate(user.getPk(), year, monthShort);
        for (EnrolledLecturesResponse one : enrolledLecturesResponseList) {
            if (Objects.equals(one.getLecture_id(), lecture_id)) {
                throw new RuntimeException("이미 개설된 강의를 다시 개설할 수 는 없습니다.");
            }
        }

        Lecture lecture = lectureRepo.findOneById(user.getPk(),lecture_id).orElseThrow(() -> new RuntimeException("강의 개설 오류! 없는 강의임 : " + lecture_id ));
        Institute institute = instituteRepo.findByPk(user.getPk()).orElseThrow(()->new RuntimeException("강의 개설 오류! 없는 학원에서의 요청 :" + user.getPk()));

        LocalDate date_info = LocalDate.of((int) year, (int) monthShort, 1);

        ///////////////////////////////////// 1. 일정 테이블 생성 (각 달의 일정에 맞추어서)
        // 강의의 시간표 가져오기
        // ("MONDAY:12:30~13:20,
        // TUESDAY:12:30~13:20,
        // WEDNESDAY:12:30~13:20")
        List<String> scheduleTimeList = List.of(lecture.getTime().split(","));
        String[] dayInfo = null; // 단위 한개의 일정을 입력받을 곳 "MONDAY:12:30~13:20" 이 분리됨 -> "MONDAY","12:30~13:20"
        String[] timeInfo = null; // 각 시작 or 종료 시점의 정보 저장되는곳 "12:30~13:20" 이 분리됨. -> "12:30","13:20"
        DayOfWeek dayOfWeek  = null; // 요일 정보를 얻기 위한. DayOfWeek.valueOf(string) 으로 변환 가능


        // 강의의 큰 일정마다
        for(String schedule_time : scheduleTimeList){
            dayInfo = schedule_time.split("-"); // dayInfo[0] = 요일, dayInfo[1] = 시간

            // 요일과 시간을 저장.
            dayOfWeek = DayOfWeek.valueOf(dayInfo[0]);
            timeInfo = dayInfo[1].split("~"); //timeInfo[0]  = 시작시간, timeInfo[1] = 종료시간\

            // String 으로 저장된 정보를 형변환
            LocalDate first_day = date_info.with(TemporalAdjusters.firstInMonth(dayOfWeek)); // 첫 해당 요일 ( ex 첫번재 월요일)
            LocalDate last_day = date_info.with(TemporalAdjusters.lastInMonth(dayOfWeek));  // 마지막 해당 요일  ( ex 마지막 월요일)
            LocalTime start_time = LocalTime.parse(timeInfo[0] + ":00");
            LocalTime end_time = LocalTime.parse(timeInfo[1] + ":00");




            // 해당 달의 각주의 해당 요일 마다.
            LocalDate current_day = first_day;
            while (!current_day.isAfter(last_day)) {
                // 시작, 종료 시간 설정
                LocalDateTime start_date = LocalDateTime.of(current_day, start_time);
                LocalDateTime end_date = LocalDateTime.of(current_day, end_time);


                // 스케쥴 생성
                Schedule sc = Schedule.builder()
                        .title(lecture.getName())
                        .start_date(start_date)
                        .end_date((end_date))
                        .memo("-")
                        .institute(institute)
                        .lecture(lecture)
                        .build();

                scheduleRepo.save(sc);
                current_day = current_day.with(TemporalAdjusters.next(dayOfWeek));
            }
        }

        ///////////////////////////////////// 2. 수강 & 출석 테이블 생성
        // 각 학생별로 강의에 대한 수강 요소, 출석 요소를 넣는다.
        // 그전에 이번달 전체 요금 테이블이 없으면 만들어두기.
        Optional<Fee> feeTuple = feeRepo.findByInstituteDate(user.getPk(), year, monthShort);
        if (feeTuple.isEmpty()) {
            feeRepo.save(Fee.builder()
                    .institute(institute)
                    .totalMonthFee(0)
                    .year(year)
                    .month(monthShort)
                    .build());
        }

        for (Long student_id : request.getStudentIdList()) {
            addOneStudentToEnroll(user, lecture_id, student_id, (short) year, monthShort);
        }
        return new EnrolledLecturesResponse(lecture_id, lecture.getName(), (short)year, monthShort, lecture.getFee());
    }

    // 학생 한명이 강의를 수강하는 요청
    // 수강 테이블과 출석 테이블을 생성한다.
    @Override
    @Transactional
    public EnrollResponse addOneStudentToEnroll(CustomUser user, Long lecture_id, Long student_id, Short year, Short month) {

        List<EnrolledStudentsResponse> enrolledStudentsResponseList = enrollRepo.findEnrolledStudentsByDate(user.getPk(), lecture_id, year, month);
        for (EnrolledStudentsResponse one : enrolledStudentsResponseList) {
            if (Objects.equals(one.getStudent_id(), student_id)) {
                throw new RuntimeException("이미 등록한 학생을 또 등록할 순 없습니다.");
            }
        }

        Student student = studentRepo.findById(user.getPk(), student_id).orElseThrow(() -> new RuntimeException("학생->수강 오류! 없거나 접근 불가능한 학생임 : " + student_id ));
        Lecture lecture = lectureRepo.findOneById(user.getPk(), lecture_id).orElseThrow(() -> new RuntimeException("강의->수강 오류! 없거나 접근 불가능한 강의임 : " + lecture_id ));


        // 1. 출석 테이블 생성
        // 해당 강의 스케줄 일정 가져오기
        LocalDate date_info = LocalDate.of(year, month, 15);
        Set<Schedule> scheduleSet = scheduleRepo.filter_by_lecture(user.getPk(), lecture_id, date_info);

        if (scheduleSet.isEmpty()) {
            throw new RuntimeException("개설되지 않은 강의에 대한 수강신청 접근이 있었습니다.");
        }

        // 각 일정마다 학생의 출석 테이블 생성
        for (Schedule schedule : scheduleSet) {
            Attend newAttend = new Attend((byte)0,"-", student, schedule);
            attendRepo.save(newAttend);
        }


        // 2. 수강 테이블 생성
        Enroll newEnroll = new Enroll(lecture, student, year, month);
        enrollRepo.save(newEnroll);
        System.out.println("newEnroll = " + newEnroll);
        
        //// 수강료에 추가.
        feeRepo.addMonthTotalAndPaidFee(user.getPk(), year, month, lecture.getFee(), 0);


        return new EnrollResponse(newEnroll);
    }

    //////////////////////////////////////////////////////////
    ///                       삭제                           //
    //////////////////////////////////////////////////////////
    
    @Override
    @Transactional
    public String deleteOneStudentFromEnroll(CustomUser user, Long enroll_id, Long lecture_id, Long student_id, Short year, Short month) {
        Lecture lecture = lectureRepo.findOneById(user.getPk(), lecture_id).orElseThrow(()->new RuntimeException("존재하지 않거나 권한이 없는 강의 정보에 접근하려함"));
        Enroll enroll = enrollRepo.findById(user.getPk(), enroll_id).orElseThrow(()-> new RuntimeException("존재하지 않거나 권한이 없는 수강 정보에 접근하려함."));
        feeRepo.declineMonthTotalAndPaidFee(user.getPk(), year, month, lecture.getFee(), enroll.getPayed_fee() );

        // 만약 해당 강의를 수강하는 학생이 없다면, 스케쥴도 삭제.
        if (enrollRepo.findEnrolledStudentsByDate(user.getPk(), lecture_id, year, month).size() == 1) {
            System.out.println("강의를 수강하는 학생이 없다.");
            scheduleRepo.deleteByLectureDate(user.getPk(),lecture_id, LocalDate.of((int) year, (int) month, 1));
        }else {
            // 아직 해당 강의를 수강하는 학생이 있다면, 수강 취소하는 학생의 출석부만 삭제
            System.out.println("강의를 수강하는 학생이 아직 남아 있다.");
            attendRepo.deleteMonthAttend(user.getPk(), lecture_id, student_id, LocalDate.of((int) year, (int) month, 1));
        }

        return enrollRepo.delete(user.getPk(), enroll_id);
    }

}
