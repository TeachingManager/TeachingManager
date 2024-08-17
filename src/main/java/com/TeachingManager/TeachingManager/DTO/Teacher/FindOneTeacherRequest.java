package com.TeachingManager.TeachingManager.DTO.Teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class FindOneTeacherRequest {
    private UUID teacher_id;
}
