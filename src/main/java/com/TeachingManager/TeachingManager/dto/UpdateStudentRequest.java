package com.TeachingManager.TeachingManager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateStudentRequest {
    private String name;
    private int age;
    private int grade;
    private String phoneNumber;
    private String parentName;
    private String parentNumber;
    private String gender;
    private String level;
}
