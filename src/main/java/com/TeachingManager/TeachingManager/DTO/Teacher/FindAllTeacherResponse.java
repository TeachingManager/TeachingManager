package com.TeachingManager.TeachingManager.DTO.Teacher;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class FindAllTeacherResponse {
    Set<TeacherInfo> teacherList;
}
