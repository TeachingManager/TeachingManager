package com.TeachingManager.TeachingManager.DTO.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MonthScheduleRequest {
        private LocalDate date_info;
}
