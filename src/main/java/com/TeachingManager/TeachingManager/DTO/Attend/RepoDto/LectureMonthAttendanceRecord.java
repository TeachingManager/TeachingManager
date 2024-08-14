package com.TeachingManager.TeachingManager.DTO.Attend.RepoDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
// 특정 달의 특정 강의를 수강하는 모든 학생의 출결 정보를 담는 DTO
public class LectureMonthAttendanceRecord {
    private Long attend_id;
    private Byte attendance;
    private Long student_id;
    private String student_name;
    private LocalDateTime start_date;

    public LectureMonthAttendanceRecord(  Long attend_id,Long student_id, String student_name, Byte attendance, LocalDateTime start_date ) {
        this.attend_id = attend_id;
        this.student_id = student_id;
        this.attendance = attendance;
        this.student_name = student_name;
        this.start_date = start_date;
    }

}
