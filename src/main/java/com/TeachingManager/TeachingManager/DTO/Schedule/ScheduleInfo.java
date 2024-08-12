package com.TeachingManager.TeachingManager.DTO.Schedule;


import com.TeachingManager.TeachingManager.domain.Institute;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScheduleInfo {
    private Long schedule_id;
    private String title;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String memo;
    private Long institute_id;
    private Long lecture_id;
    private String lecture_name;
}
