package com.TeachingManager.TeachingManager.DTO.Attend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class MonthStudentAttendResponse {
    private Long student_id;
    private Long lecture_id;
    private String lecture_name;
    private List<SimpleAttendInfo> attendInfoList;
}
