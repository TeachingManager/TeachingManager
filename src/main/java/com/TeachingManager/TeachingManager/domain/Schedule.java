package com.TeachingManager.TeachingManager.domain;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @Column(name="schedule_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedule_id; // 시스템이 분류할때 쓰는 id  즉 pk

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="start_date", nullable = false)
    private LocalDateTime start_date;

    @Column(name="end_date", nullable = false)
    private LocalDateTime end_date;

    @Column(name="memo", nullable = true)
    private String memo;

    @Column(name="institute_id", nullable = false)
    private Long institute_id;

    // 강의 외래키
    private Long cid;


    @Builder
    public Schedule(String title, LocalDateTime start_date, LocalDateTime end_date, String memo, Long institute_id){
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.memo = memo;
        this.institute_id = institute_id;
    }

    public void update(String title, LocalDateTime start_date, LocalDateTime end_date, String memo) {
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.memo = memo;
    }
}
