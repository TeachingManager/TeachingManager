package com.TeachingManager.TeachingManager.DTO.Attend.RepoDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
// 특정학생의 특정강의의 특정 달의 출석 정보를 가져오는 DTO
public class StudentMonthLectureAttendRecord {
    private Long student_id;
    private Long lecture_id;
    private Byte attendance;
    private LocalDateTime start_date;
}
