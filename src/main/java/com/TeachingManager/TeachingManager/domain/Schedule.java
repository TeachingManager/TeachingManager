package com.TeachingManager.TeachingManager.domain;


import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    // Institute와의 Many-to-One 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Institute institute;


  //   강의 일대 다 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture;


    @Builder
    public Schedule(String title, LocalDateTime start_date, LocalDateTime end_date, String memo, Institute institute, Lecture lecture){
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.memo = memo;
        this.institute = institute;
        this.lecture  = lecture;
    }

    public void update(String title, LocalDateTime start_date, LocalDateTime end_date, String memo) {
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.memo = memo;
    }
}
