package com.TeachingManager.TeachingManager.DTO.Attend.RepoDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
// 특정 학생의 특정 달에 듣는 모든 강의의 출석 정보를 저장하는 DTO
public class StudentsMonthAttendRecord {
    private Long attend_id;
    private Byte attendance;
    private Long lecture_id;
    private String lecture_name; //강의명
    private LocalDateTime start_date;

    public StudentsMonthAttendRecord(Long attend_id, Long lecture_id, String lecture_name, Byte attendance, LocalDateTime start_date){
        this.attendance = attendance;
        this.lecture_id = lecture_id;
        this.start_date = start_date;
        this.attend_id = attend_id;
        this.lecture_name = lecture_name;
    }

}
