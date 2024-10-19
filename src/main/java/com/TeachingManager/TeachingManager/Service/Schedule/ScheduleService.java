package com.TeachingManager.TeachingManager.Service.Schedule;


import com.TeachingManager.TeachingManager.DTO.Schedule.AddScheduleRequest;
import com.TeachingManager.TeachingManager.DTO.Schedule.*;
import com.TeachingManager.TeachingManager.DTO.Schedule.MonthScheduleResponse;
import com.TeachingManager.TeachingManager.DTO.Schedule.UpdateScheduleRequest;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Schedule;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public interface ScheduleService {


    // 내부에서 scid 를 만든다.
    Schedule create_schedule(AddScheduleRequest request, CustomUser user);


    Schedule update_schedule(CustomUser user, Long scid, UpdateScheduleRequest request);

    void delete_schedule(CustomUser user, Long scid);


    /* 검색 관련 */
//    디테일 검색
    Schedule search_schedule(CustomUser user, Long schedule_id);

    // 목록 검색

    List<Map<String, String>> search_all_marker(UUID institute_id);
//    전체 검색

    MonthScheduleResponse searchAll_schedule(CustomUser user);


//    날짜 단위
// 특정 달의 일정 가져오기
    MonthScheduleResponse searchAll_scheduleByDate(CustomUser user, LocalDate date_info);

// 특정 하루의 일정 가져오기
    DayScheduleResponse searchAll_scheduleByDay(CustomUser user, LocalDate date_info);
}
