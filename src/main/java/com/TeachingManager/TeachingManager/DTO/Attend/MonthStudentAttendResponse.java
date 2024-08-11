package com.TeachingManager.TeachingManager.DTO.Attend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class MonthStudentAttendResponse {
    private Long attend_id;
    private Long student_id;
    private String student_name;
    private Set<Byte> attendaneSet;
}
