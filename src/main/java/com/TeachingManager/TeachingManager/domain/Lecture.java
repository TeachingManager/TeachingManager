package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.*;

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
//    private LocalTime startTime;
//    private LocalTime endTime;
    private String category;
    private String grade;
    private int fee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", nullable = false)
    private Institute teacher;


//    @Builder
//    public Lecture(Long lecture_id, String name, LocalTime){
//        this.title = title;
//        this.start_date = start_date;
//        this.end_date = end_date;
//        this.memo = memo;
//        this.institute = institute;
//    }
//
//    public void update(String title, LocalTime start_date, LocalTime end_date, String memo) {
//        this.title = title;
//        this.start_date = start_date;
//        this.end_date = end_date;
//        this.memo = memo;
//    }
}
