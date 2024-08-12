package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enroll {
    @Id
    @Column(name="enroll_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enroll_id;

    @Column(name = "year", nullable = false)
    private Short year;

    @Column(name = "month", nullable = false)
    private Short month;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Builder
    public Enroll(Lecture lecture, Student student, Short year, Short month) {
        this.year = year;
        this.month = month;
        this.lecture = lecture;
        this.student = student;
    }
}
