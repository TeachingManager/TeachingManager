package com.TeachingManager.TeachingManager.DTO.Enroll.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EnrolledLecturesResponse {
    private Long lecture_id;
    private String lecture_name;
    private LocalDate date_info;
}