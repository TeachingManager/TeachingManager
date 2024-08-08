package com.TeachingManager.TeachingManager.DTO.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MonthScheduleRequest {
        private LocalDate date_info;
}
