package com.TeachingManager.TeachingManager.DTO.Lecture;

import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Lecture;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddLectureRequest {

    private String name;
    private String category;
    private String grade;
    private int fee;
    private String time;
    private UUID teacherId;

    public Lecture toEntity(Institute institute, Teacher teacher) {
        return Lecture.builder()
                .name(name)
                .category(category)
                .grade(grade)
                .fee(fee)
                .time(time)
                .institute(institute)
                .teacher(teacher)
                .build();
    }

}
