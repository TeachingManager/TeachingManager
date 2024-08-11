package com.TeachingManager.TeachingManager.controller.API.Enroll;

import com.TeachingManager.TeachingManager.DTO.Enroll.Request.EnrollLectureRequest;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrollResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledStudentsResponse;
import com.TeachingManager.TeachingManager.Service.Enroll.EnrollService;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ApiEnrollController {
    private final EnrollService enrollService;

    // 특정달의 이 강의를 수강한 학생들을 요청하는 API
    @GetMapping("/api/enroll/students")
    public ResponseEntity<EnrolledStudentsResponse> findEnrolledStudents(
            @AuthenticationPrincipal CustomUser user
            , @RequestParam(value = "lecture_id") String lecture_id
            , @RequestParam(value = "date_info") LocalDate date_info) {
        return ResponseEntity.ok().build();
    }

    // 특정달에 개설된 강의 리스트
    @GetMapping("/api/enroll/lectures")
    public ResponseEntity<EnrolledLecturesResponse> findEnrolledLectures(
            @AuthenticationPrincipal CustomUser user
            , @RequestParam(value = "date_info") LocalDate date_info) {
        return ResponseEntity.ok().build();
    }

    // 수강신청 & 등록 api
    @PostMapping("/api/enroll")
    public ResponseEntity<EnrolledLecturesResponse> enrollLectures(
            @AuthenticationPrincipal CustomUser user
            , @RequestParam(value = "lecture_id") Long lecture_id
            , @RequestBody EnrollLectureRequest request
            ) {
         // 3가지 로직이 필요하다.
        // 우선 request 안의 학생들 정보리스트와 lecture_id 를 이용하여 수강 저장 (이번달이어야함. 갑자기 지난달 으로는 추가 불가..)
        // 강의를 이번달 일정에 추가 (역시 이번달이여야함)
        // 일정들을 생성하는 과정 중간에 해당 일정들과 해당 학생들의 출석 튜플 생성해야함.
        // 중요한 것은 이미 해당 강의가 수강 테이블에 존재하는지 체크하고 없을 때에만 작동해야함.
        return ResponseEntity.ok().build();
    }


    // 단일 학생 수강등록 api
    @PostMapping("/api/enroll/student")
    public ResponseEntity<EnrollResponse> enrollStudentToLecture(
            @AuthenticationPrincipal CustomUser user
            , @RequestParam(value = "lecture_id") Long lecture_id
            , @RequestParam(value = "student_id") Long student_id) {
        // 이미 이번 달에 수강신청 & 등록이 되어있는 강의에 추가하여야함.
        // 아직 수강등록이 안되어있는 강의에는 학생이 따로 수강신청을 할 수 없음.  그럴 경우에는 수강신청 & 등록  api 를 짜야함.
        return ResponseEntity.ok().build();
    }

    // 학생의 특정달 수강 취소 api
    @PutMapping("/api/delete/enroll/student")
    public ResponseEntity<String> cancelStudentEnroll(
            @AuthenticationPrincipal CustomUser user
            , @RequestParam(value = "lecture_id") Long lecture_id
            , @RequestParam(value = "student_id") Long student_id
            , @RequestParam(value = "date_info") LocalDate date_info) {
        // 특정달의 특정강의의 특정학생의 수강신청 여부 삭제
        return ResponseEntity.ok().build();
    }

}
