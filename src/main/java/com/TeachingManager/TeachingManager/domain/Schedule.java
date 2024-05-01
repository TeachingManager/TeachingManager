package com.TeachingManager.TeachingManager.domain;


import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalDateTime;

public class Schedule {


    private Long schedule_id; // 시스템이 분류할때 쓰는 id  즉 pk
    private String title;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String memo;
    
// 강의 외래키
    private Long cid;



    public Long getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(Long schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }
}
