package com.TeachingManager.TeachingManager.DTO.Schedule;

import com.TeachingManager.TeachingManager.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
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
