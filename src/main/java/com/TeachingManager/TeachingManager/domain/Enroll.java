package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(name="payed_fee", nullable = false)
    private int payed_fee = 0;

    @Column(name="fullPayment", nullable = false)
    private Boolean fullPayment = false; // 0 이면 미납, 1이면 완납

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    @Builder
    public Enroll(Lecture lecture, Student student, Short year, Short month) {
        this.year = year;
        this.month = month;
        this.lecture = lecture;
        this.student = student;
    }

    public void updatePaidFee(int payed_amount){
        this.payed_fee = payed_amount;
        this.fullPayment = this.payed_fee >= lecture.getFee();
    }
}