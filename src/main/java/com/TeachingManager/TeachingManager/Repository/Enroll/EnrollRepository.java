package com.TeachingManager.TeachingManager.Repository.Enroll;

import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledStudentsResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.NotEnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.DTO.Fee.EnrollFeeResponse;
import com.TeachingManager.TeachingManager.domain.Enroll;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollRepository {

    //////////////////////////////////////////////////////////
    ///                       조회                           //
    //////////////////////////////////////////////////////////
    
    // 특정 강의에 등록된던 학생들
    List<EnrolledStudentsResponse> findEnrolledStudentsByDate(UUID institute_id, Long lecture_id, Short year, Short month);

    // 특정달에 개설되었던 강의들
    List<EnrolledLecturesResponse> findEnrolledLecturesByDate(UUID institute_id, Short year, Short month);

    List<EnrolledLecturesResponse> findEnrolledLecturesByTeacherAndDate(UUID institute_id, UUID teacher_id, Short year, Short month);

    // 특정달에 개설되지 않았던 강의들
    List<NotEnrolledLecturesResponse> findNotEnrolledLecturesByDate(UUID institute_id, Short year, Short month);

    // 단일 검색
    Optional<Enroll> findById(UUID institute_id, Long enroll_id);

    // 수강에서 학원비 정보 가져오기
    List<EnrollFeeResponse> findEnrolledFeeByDate(UUID institute_id, Short year, Short month);

    //////////////////////////////////////////////////////////
    ///                       추가                           //
    //////////////////////////////////////////////////////////
    // 저장
    Enroll save(Enroll enroll);

    //////////////////////////////////////////////////////////
    ///                       삭제                           //
    //////////////////////////////////////////////////////////
    // 삭제
    String delete(UUID institute_id, Long enroll_id);
}
