package com.TeachingManager.TeachingManager.DTO.Attend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class MonthLectureAttendResponse {
    private Long lecture_id;
    private Long student_id;
    private String student_name;
    private List<SimpleAttendInfo> attendInfoList;
}
