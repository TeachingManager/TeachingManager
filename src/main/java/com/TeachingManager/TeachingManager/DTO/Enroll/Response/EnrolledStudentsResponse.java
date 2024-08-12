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
    private LocalDate date_info;
}
