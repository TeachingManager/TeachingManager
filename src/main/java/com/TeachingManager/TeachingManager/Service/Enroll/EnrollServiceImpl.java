package com.TeachingManager.TeachingManager.Service.Enroll;

import com.TeachingManager.TeachingManager.DTO.Enroll.Request.EnrollLectureRequest;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrollResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledStudentsResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.NotEnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollServiceImpl implements EnrollService{
    @Override
    public List<EnrolledStudentsResponse> findMonthlyEnrolledStudents(CustomUser user, Long lecture_id, LocalDate date_info) {
        return null;
    }

    @Override
    public List<EnrolledLecturesResponse> findMonthlyEnrolledLectures(CustomUser user, LocalDate date_info) {
        return null;
    }

    @Override
    public List<NotEnrolledLecturesResponse> findMonthlyNotEnrolledLectures(CustomUser user, LocalDate date_info) {
        return null;
    }

    @Override
    public EnrolledLecturesResponse registerEnroll(CustomUser user, Long lecture_id, EnrollLectureRequest request) {
        return null;
    }

    @Override
    public EnrollResponse addOneStudentToEnroll(CustomUser user, Long lecture_id, Long student_id, LocalDate date_info) {
        return null;
    }

    @Override
    public String deleteOneStudentFromEnroll(CustomUser user, Long lecture_id, Long student_id, LocalDate date_info) {
        return null;
    }
}
