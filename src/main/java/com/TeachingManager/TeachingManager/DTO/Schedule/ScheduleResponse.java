package com.TeachingManager.TeachingManager.DTO.Schedule;

import com.TeachingManager.TeachingManager.domain.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ScheduleResponse {
    private String title;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String memo;

    public ScheduleResponse( Schedule sc) {
        this.title = sc.getTitle();
        this.start_date = sc.getStart_date();
        this.end_date = sc.getEnd_date();
        this.memo = sc.getMemo();
    }
}
