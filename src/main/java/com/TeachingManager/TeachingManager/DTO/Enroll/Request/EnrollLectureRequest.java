package com.TeachingManager.TeachingManager.DTO.Enroll.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EnrollLectureRequest {
    List<Long> studentIdList;
}
