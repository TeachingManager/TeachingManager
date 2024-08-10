package com.TeachingManager.TeachingManager.DTO.Attend;

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
}
