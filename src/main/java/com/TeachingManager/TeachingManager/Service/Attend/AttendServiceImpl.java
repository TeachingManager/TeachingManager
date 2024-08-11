package com.TeachingManager.TeachingManager.Service.Attend;

import com.TeachingManager.TeachingManager.DTO.Attend.AttendInfo;
import com.TeachingManager.TeachingManager.DTO.Attend.MonthLectureAttendResponse;
import com.TeachingManager.TeachingManager.DTO.Attend.MonthStudentAttendResponse;
import com.TeachingManager.TeachingManager.DTO.Attend.Request.UpdateAttendListRequest;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AttendServiceImpl implements AttendService{
    @Override
    public AttendInfo createSingleAttend(CustomUser user, Long schedule_id, Long student_id) {
        return null;
    }

    @Override
    public AttendInfo searchById(CustomUser user, Long attend_id) {
        return null;
    }

    @Override
    public MonthLectureAttendResponse findMonthlyLectureAttendance(CustomUser user, Long lecture_id, LocalDate date_info) {
        return null;
    }

    @Override
    public MonthStudentAttendResponse findMonthlyStudentAttendance(CustomUser user, Long student_id, LocalDate date_info) {
        return null;
    }

    @Override
    public String updateAttends(CustomUser user, UpdateAttendListRequest request) {
        return null;
    }

    @Override
    public String deleteSingleAttend(CustomUser user, Long attend_id) {
        return null;
    }
}
