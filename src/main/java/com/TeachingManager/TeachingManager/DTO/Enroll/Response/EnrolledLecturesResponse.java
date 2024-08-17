package com.TeachingManager.TeachingManager.DTO.Enroll.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EnrolledLecturesResponse {
    private Long lecture_id;
    private String lecture_name;
    private Short year;
    private Short month;
    private int fee;

    public EnrolledLecturesResponse(Long lecture_id, String lecture_name, Short year, Short month, int fee) {
        this.lecture_id = lecture_id;
        this.lecture_name = lecture_name;
        this.year = year;
        this.month = month;
        this.fee = fee;
    }
}
