package com.TeachingManager.TeachingManager.DTO.Teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FireTeacherRequest {
    private UUID teacherPK;
}
