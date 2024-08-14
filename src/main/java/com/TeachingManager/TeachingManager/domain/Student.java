package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "grade", nullable = false)
    private int grade;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "parentName", nullable =false)
    private String parentName;

    @Column(name = "parentNumber", nullable = false)
    private String parentNumber;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "level", nullable = false)
    private String level;

    @ManyToOne
    @JoinColumn(name = "institute_id", nullable = false)
    private Institute institute;

    @Builder
    public Student(String name, int age, int grade, String phoneNumber, String parentName, String parentNumber, String gender, String level, Institute institute) {
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.phoneNumber = phoneNumber;
        this.parentName = parentName;
        this.parentNumber = parentNumber;
        this.gender = gender;
        this.level = level;
        this.institute = institute;
    }

    //업데이트 함수
    public void update(String name, int age, int grade, String phoneNumber, String parentName, String parentNumber, String gender, String level){
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.phoneNumber = phoneNumber;
        this.parentName = parentName;
        this.parentNumber = parentNumber;
        this.gender = gender;
        this.level = level;
    }
}
