package com.TeachingManager.TeachingManager.DTO.Schedule;

import com.TeachingManager.TeachingManager.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddScheduleRequest {

    private String title;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String memo;

    public Schedule toEntity() {
        return Schedule.builder()
                .title(title)
                .start_date(start_date)
                .end_date(end_date)
                .memo(memo)
                .build();
    }

}
