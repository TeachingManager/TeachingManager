package com.TeachingManager.TeachingManager.DTO.Attend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class MonthLectureAttendResponse {
    private Long lecture_id;
    private String lecture_title;
    private Set<Byte> attendaneSet;
}
