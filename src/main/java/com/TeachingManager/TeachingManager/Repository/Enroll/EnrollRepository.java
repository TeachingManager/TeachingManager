package com.TeachingManager.TeachingManager.Repository.Enroll;

import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledStudentsResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.NotEnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.domain.Enroll;

import java.util.List;
import java.util.Optional;

public interface EnrollRepository {

    //////////////////////////////////////////////////////////
    ///                       조회                           //
    //////////////////////////////////////////////////////////
    
    // 특정 강의에 등록된던 학생들
    List<EnrolledStudentsResponse> findEnrolledStudentsByDate(Long institute_id, Long lecture_id, Short year, Short month);

    // 특정달에 개설되었던 강의들
    List<EnrolledLecturesResponse> findEnrolledLecturesByDate(Long institute_id, Short year, Short month);

    // 특정달에 개설되지 않았던 강의들
    List<NotEnrolledLecturesResponse> findNotEnrolledLecturesByDate(Long institute_id, Short year, Short month);

    // 단일 검색
    Optional<Enroll> findById(Long institute_id, Long enroll_id);

    //////////////////////////////////////////////////////////
    ///                       추가                           //
    //////////////////////////////////////////////////////////
    // 저장
    Enroll save(Enroll enroll);

    //////////////////////////////////////////////////////////
    ///                       삭제                           //
    //////////////////////////////////////////////////////////
    // 삭제
    String delete(Long institute_id, Long enroll_id);
}
