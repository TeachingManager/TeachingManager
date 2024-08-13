package com.TeachingManager.TeachingManager.Service.Enroll;

import com.TeachingManager.TeachingManager.DTO.Enroll.Request.EnrollLectureRequest;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.*;
import com.TeachingManager.TeachingManager.domain.CustomUser;

import java.util.List;

public interface EnrollService {



    //////////////////////////////////////////////////////////
    ///                       조회                           //
    //////////////////////////////////////////////////////////

    // 특정 달의 이 강의를 수강한 학생들을 요청하는 API
    List<EnrolledStudentsResponse> findMonthlyEnrolledStudents(CustomUser user, Long lecture_id, Short year, Short month);

    // 특정 달에 개설된 강의 리스트
    List<EnrolledLecturesResponse> findMonthlyEnrolledLectures(CustomUser user, Short year, Short month);

    // 특정 달에 개설되지 않은 강의 리스트
    List<NotEnrolledLecturesResponse> findMonthlyNotEnrolledLectures(CustomUser user, Short year, Short month);

    // 특정 달의 요금 정보를 가져오는 API
    EnrollFeeResponse findMonthlyLectureFee(CustomUser user, Short year, Short month);


    //////////////////////////////////////////////////////////
    ///                       추가                           //
    //////////////////////////////////////////////////////////

    // 수강신청
    EnrolledLecturesResponse registerEnroll(CustomUser user, Long lecture_id, EnrollLectureRequest request);
    
    
    // 한명이 이미 Enroll 에 들어있는 강의에 추가되기
    EnrollResponse addOneStudentToEnroll(CustomUser user, Long lecture_id, Long student_id, Short year, Short month);
    


    //////////////////////////////////////////////////////////
    ///                       삭제                           //
    //////////////////////////////////////////////////////////

    String deleteOneStudentFromEnroll(CustomUser user, Long enroll_id);

}
