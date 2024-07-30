package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id", updatable = false)
    private long lectureId;

    @Column(name = "lecture_name", nullable = false)
    private String lectureName;

    @Column(name = "day", nullable = false)
    private String day;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "category")
    private int category;

    @Column(name = "grade")
    private int grade;

    @Column(name = "fee", nullable = false)
    private long fee;

    // teacher 외래키
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "teacher_id", nullable = false)
//    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", nullable = false)
    private Institute institute;

    // 연관관계 편의 메서드
//    public void setInstitute(Institute institute) {
//        this.institute = institute;
//        institute.getLectures().add(this);
//    }

}
