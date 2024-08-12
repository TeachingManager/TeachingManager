package com.TeachingManager.TeachingManager.DTO.Attend;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class SimpleAttendInfo {
    private Long attend_id;
    private Byte attendance;
    private LocalDate start_date;

    public  SimpleAttendInfo(Long attend_id, Byte attendance, LocalDateTime start_date) {
        this.attend_id = attend_id;
        this.attendance = attendance;
        this.start_date = start_date.toLocalDate();
    }
}
