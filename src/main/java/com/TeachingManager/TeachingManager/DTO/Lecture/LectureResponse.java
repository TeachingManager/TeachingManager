package com.TeachingManager.TeachingManager.DTO.Lecture;

import com.TeachingManager.TeachingManager.domain.Lecture;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LectureResponse {

    private Long id;
    private String name;
    private String category;
    private String grade;
    private int fee;
    private String time;
    private Teacher teacher;

    public LectureResponse(Lecture lecture) {
        this.id = lecture.getLecture_id();
        this.name = lecture.getName();
        this.category = lecture.getCategory();
        this.grade = lecture.getGrade();
        this.fee = lecture.getFee();
        this.time = lecture.getTime();
        this.teacher = lecture.getTeacher();
    }

}
