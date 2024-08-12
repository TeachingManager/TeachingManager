package com.TeachingManager.TeachingManager.DTO.Enroll.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EnrolledStudentsResponse {
    private Long lecture_id;
    private Long student_id;
    private String student_name;
    private Short year;
    private Short month;

    public EnrolledStudentsResponse(Long lecture_id, Long student_id, String student_name, Short year, Short month) {
        this.lecture_id = lecture_id;
        this.student_id = student_id;
        this.student_name = student_name;
        this.year = year;
        this.month = month;
    }
}
