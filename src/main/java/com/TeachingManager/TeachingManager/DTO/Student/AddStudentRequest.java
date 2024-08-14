package com.TeachingManager.TeachingManager.DTO.Student;

import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Student;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddStudentRequest {

    private String name;
    private int age;
    private int grade;
    private String phoneNumber;
    private String parentName;
    private String parentNumber;
    private String gender;
    private String level;

    public Student toEntity(Institute institute){
        return Student.builder()
                .name(name)
                .age(age)
                .grade(grade)
                .phoneNumber(phoneNumber)
                .parentName(parentName)
                .parentNumber(parentNumber)
                .gender(gender)
                .level(level)
                .institute(institute)
                .build();
    }
}
