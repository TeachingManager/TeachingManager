package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalTime;


@Getter
@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Table(name = "lecture")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id", updatable = false)
    private Long lecture_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "fee", nullable = false)
    private int fee;

    @Column(name = "time", nullable = false)
    private String time;

    // 학원과의 일대다 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Institute institute;

    // 선생님와의 일대다 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_DEFAULT)
    private Teacher teacher = null;


    @Builder
    public Lecture(String name, String category, String grade, int fee, String time, Institute institute, Teacher teacher){
        this.name = name;
        this.category = category;
        this.grade = grade;
        this.fee = fee;
        this.time= time;
        this.institute = institute;
        this.teacher = teacher;
    }

    public void update(String name, String category, String grade, int fee, String time, Teacher teacher) {
        this.name = name;
        this.category = category;
        this.grade = grade;
        this.fee = fee;
        this.time= time;
        this.teacher = teacher;
    }

}
