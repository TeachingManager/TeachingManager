package com.TeachingManager.TeachingManager.DTO.Attend;

import com.TeachingManager.TeachingManager.domain.Attend;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class AttendInfo {
    private Long attend_id;
    private Byte attendance;
    private String memo;
    private Long student_id;
    private String student_name;
    private Long schedule_id;
    private String schedule_title;
    private LocalDateTime schedule_startTime;

    public AttendInfo(Attend attend) {
        this.attend_id = attend.getAttend_id();
        this.attendance = attend.getAttendance();
        this.memo = attend.getMemo();
        this.student_id = attend.getStudent().getId();
        this.student_name = attend.getStudent().getName();
        this.schedule_id = attend.getSchedule().getSchedule_id();
        this.schedule_title = attend.getSchedule().getTitle();
        this.schedule_startTime = attend.getSchedule().getStart_date();
    }
}
