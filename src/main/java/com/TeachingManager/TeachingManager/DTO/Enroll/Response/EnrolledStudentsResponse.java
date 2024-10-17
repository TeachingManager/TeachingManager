package com.TeachingManager.TeachingManager.DTO.Enroll.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EnrolledStudentsResponse {
    private Long lecture_id;
    private Long student_id;
    private Long enroll_id;
    private String student_name;
    private Short year;
    private Short month;
    private int lecture_fee;
    private int payed_fee;
    private Boolean fullPaid;

    public EnrolledStudentsResponse(Long lecture_id, Long student_id, Long enroll_id, String student_name, Short year, Short month,  int payed_fee, Boolean fullPaid, int lecture_fee) {
        this.lecture_id = lecture_id;
        this.student_id = student_id;
        this.enroll_id = enroll_id;
        this.student_name = student_name;
        this.year = year;
        this.month = month;
        this.payed_fee = payed_fee;
        this.fullPaid = fullPaid;
        this.lecture_fee = lecture_fee;
    }
}
