package com.TeachingManager.TeachingManager.DTO.Lecture;

import com.TeachingManager.TeachingManager.domain.Lecture;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateLectureRequest {

    private String name;
    private String category;
    private String grade;
    private int fee;
    private String time;
    private Long teacherId;

}
