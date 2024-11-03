package com.TeachingManager.TeachingManager.DTO.Schedule;

import com.TeachingManager.TeachingManager.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class ScheduleResponse {
    private Long schedule_id;
    private String title;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String memo;
    private Long lecture_id;
    private String lecture_name;
    private String teacher_name;

    public ScheduleResponse(Long schedule_id, String title, LocalDateTime start_date, LocalDateTime end_date, String memo, Long lecture_id, String lecture_name,String teacher_name) {
        this.schedule_id = schedule_id;
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.memo = memo;
        this.lecture_id = lecture_id;
        this.lecture_name = lecture_name;
        this.teacher_name = teacher_name;
    }

    public ScheduleResponse( Schedule sc) {
        this.schedule_id = sc.getSchedule_id();
        this.title = sc.getTitle();
        this.start_date = sc.getStart_date();
        this.end_date = sc.getEnd_date();
        this.memo = sc.getMemo();
        if(sc.getLecture() != null) {
            this.lecture_name = sc.getLecture().getName();
            this.lecture_id = sc.getLecture().getLecture_id();
            this.teacher_name = sc.getLecture().getTeacher().getTeacher_name();
        }
        else{
            this.lecture_name = "기본강의";
            this.lecture_id = 0L;
        }
    }
}
