package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attend {

    @Id
    @Column(name="attend_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attend_id; // 시스템이 분류할때 쓰는 id  즉 pk

    @Column(name="attendance", nullable = false)
    private Byte attendance = 0; // 0 은 미정, 1점은 결석, 2점은 지각, 3점은 출석 ..... 소수들로 바꾸는 것도 생각.

    @Column(name="memo", nullable = true)
    private String memo;

    // Student 와의 Many-to-One 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Schedule 와의 Many-to-One 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Builder
    public Attend(Byte attendance, String memo, Student student, Schedule schedule){
        this.attendance = attendance;
        this.memo = memo;
        this.student = student;
        this.schedule = schedule;
    }

    public void update(Byte attendance, String memo, Student student, Schedule schedule) {
        this.attendance = attendance;
        this.memo = memo;
        this.student = student;
        this.schedule = schedule;
    }

    // 출석 정보를 저장하는 메소드.
    public Byte setAttendance(Byte attendance){
        this.attendance = attendance;
        return this.attendance;
    }

}
