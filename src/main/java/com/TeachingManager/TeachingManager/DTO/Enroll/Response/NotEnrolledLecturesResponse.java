package com.TeachingManager.TeachingManager.DTO.Enroll.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotEnrolledLecturesResponse {
    private Long lecture_id;
    private String lecture_name;

    public NotEnrolledLecturesResponse(Long lecture_id, String lecture_name) {
        this.lecture_id = lecture_id;
        this.lecture_name = lecture_name;
    }
}
