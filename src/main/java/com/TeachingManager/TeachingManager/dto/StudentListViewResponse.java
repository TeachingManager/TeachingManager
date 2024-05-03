package com.TeachingManager.TeachingManager.dto;

import com.TeachingManager.TeachingManager.domain.Student;
import lombok.Getter;

@Getter
public class StudentListViewResponse {
    private final Long id;
    private final String name;
    private final int age;
    private final int grade;
    private final String phoneNumber;
    private final String parentName;
    private final String parentNumber;
    private final String gender;
    private final String level;

    public StudentListViewResponse(Student student){
        this.id = student.getId();
        this.name = student.getName();
        this.age = student.getAge();
        this.grade = student.getGrade();
        this.phoneNumber = student.getPhoneNumber();
        this.parentName = student.getParentName();
        this.parentNumber = student.getParentNumber();
        this.gender = student.getGender();
        this.level = student.getLevel();
    }
}
