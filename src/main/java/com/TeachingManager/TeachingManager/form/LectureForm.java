package com.TeachingManager.TeachingManager.form;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;


@Getter
@Setter
public class LectureForm {
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private String category;
    private String grade;
    private int fee;

}
