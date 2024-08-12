package com.TeachingManager.TeachingManager.Service.Enroll;

import com.TeachingManager.TeachingManager.DTO.Enroll.Request.EnrollLectureRequest;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrollResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledStudentsResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.NotEnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.Repository.Attend.AttendRepository;
import com.TeachingManager.TeachingManager.Repository.Enroll.EnrollRepository;
import com.TeachingManager.TeachingManager.Repository.Lecture.LectureRepository;
import com.TeachingManager.TeachingManager.Repository.Schedule.ScheduleRepository;
import com.TeachingManager.TeachingManager.Repository.Student.StudentRepository;
import com.TeachingManager.TeachingManager.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService{

    private final EnrollRepository enrollRepo;
    private final LectureRepository lectureRepo;
    private final StudentRepository studentRepo;
    private final ScheduleRepository scheduleRepo;
    private final AttendRepository attendRepo;

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
    public EnrolledLecturesResponse registerEnroll(CustomUser user, Long lecture_id, EnrollLectureRequest request) {
        // 1. 수강 테이블 생성
        // 2. 일정 테이블 생성
        // 3. 출석 테이블 생성
        return null;
    }

    // 학생 한명이 강의를 수강하는 요청
    // 출석을 만들어야함.
    @Override
    public EnrollResponse addOneStudentToEnroll(CustomUser user, Long lecture_id, Long student_id, Short year, Short month) {
        // 1. 수강 테이블 생성
        Student student = studentRepo.findById(user.getPk(), student_id).orElseThrow(() -> new RuntimeException("학생->수강 오류! 없는 학생임 : " + student_id ));
        Lecture lecture = lectureRepo.findById(lecture_id).orElseThrow(() -> new RuntimeException("강의->수강 오류! 없는 강의임 : " + lecture_id ));
        Enroll newEnroll = new Enroll(lecture, student, year, month);
        enrollRepo.save(newEnroll);
        
        // 2. 출석 테이블 생성

        // 해당 강의 스케줄 일정 가져오기
        LocalDate date_info = LocalDate.of(year, month, 15);
        Set<Schedule> scheduleSet = scheduleRepo.filter_by_lecture(user.getPk(), lecture_id, date_info);

        // 각 일정마다 학생의 출석 테이블 생성
        for (Schedule schedule : scheduleSet) {
            Attend newAttend = new Attend((byte)0,"-", student, schedule);
            attendRepo.save(newAttend);
        }

        return new EnrollResponse(newEnroll);
    }

    //////////////////////////////////////////////////////////
    ///                       삭제                           //
    //////////////////////////////////////////////////////////
    
    @Override
    public String deleteOneStudentFromEnroll(CustomUser user, Long enroll_id) {
        return enrollRepo.delete(user.getPk(), enroll_id);
    }
}
