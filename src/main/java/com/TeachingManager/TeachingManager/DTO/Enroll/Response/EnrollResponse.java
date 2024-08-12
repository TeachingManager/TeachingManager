package com.TeachingManager.TeachingManager.DTO.Enroll.Response;

import com.TeachingManager.TeachingManager.domain.Lecture;
import com.TeachingManager.TeachingManager.domain.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class  EnrollResponse {

    private Long enroll_id;
    private Short year;
    private Short month;
    private Long lecture_id;
    private String lecture_name;
    private Long student_id;
    private String student_name;

    public EnrollResponse( Long enroll_id, Short year, Short month, Long lecture_id, String lecture_name, Long student_id, String student_name){
        this.enroll_id = enroll_id;
        this.year = year;
        this.month = month;
        this.lecture_name = lecture_name;
        this.lecture_id = lecture_id;
        this.student_id = student_id;
        this.student_name = student_name;
    }
}
