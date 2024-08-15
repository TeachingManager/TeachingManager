package com.TeachingManager.TeachingManager.DTO.Fee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollFeeResponse {
    private String student_name;
    private Long student_id;
    private String lecture_name;
    private Long lecture_id;
    private long fee;
    private String teacher_name;
    private int payed_fee;
    private Boolean fullPaid;
    private Long enroll_id;
}
