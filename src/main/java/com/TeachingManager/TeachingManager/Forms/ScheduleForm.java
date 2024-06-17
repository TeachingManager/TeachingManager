package com.TeachingManager.TeachingManager.Forms;

import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalDateTime;

public class ScheduleForm {
    private String title;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String memo;

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
}
