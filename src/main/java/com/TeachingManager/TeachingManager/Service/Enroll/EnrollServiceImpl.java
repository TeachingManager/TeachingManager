package com.TeachingManager.TeachingManager.Service.Enroll;

import com.TeachingManager.TeachingManager.DTO.Enroll.Request.EnrollLectureRequest;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrollResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledStudentsResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.NotEnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.Repository.Enroll.EnrollRepository;
import com.TeachingManager.TeachingManager.Repository.Lecture.LectureRepository;
import com.TeachingManager.TeachingManager.Repository.Schedule.ScheduleRepository;
import com.TeachingManager.TeachingManager.Repository.Student.StudentRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService{

    private final EnrollRepository enrollRepo;
    private final LectureRepository lectureRepo;
    private final StudentRepository studentRepo;
    private final ScheduleRepository scheduleRepo;

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
    @Override
    public EnrolledLecturesResponse registerEnroll(CustomUser user, Long lecture_id, EnrollLectureRequest request) {
        return null;
    }

    @Override
    public EnrollResponse addOneStudentToEnroll(CustomUser user, Long lecture_id, Long student_id, Short year, Short month) {
        return null;
    }

    //////////////////////////////////////////////////////////
    ///                       삭제                           //
    //////////////////////////////////////////////////////////
    
    @Override
    public String deleteOneStudentFromEnroll(CustomUser user, Long enroll_id) {
        return enrollRepo.delete(user.getPk(), enroll_id);
    }
}
