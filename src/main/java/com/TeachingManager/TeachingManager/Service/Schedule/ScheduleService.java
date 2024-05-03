package com.TeachingManager.TeachingManager.Service.Schedule;


import com.TeachingManager.TeachingManager.DTO.Schedule.AddScheduleRequest;
import com.TeachingManager.TeachingManager.DTO.Schedule.UpdateScheduleRequest;
import com.TeachingManager.TeachingManager.domain.Schedule;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScheduleService {


    // 내부에서 scid 를 만든다.
    Schedule create_schedule(AddScheduleRequest request);

    //    강의 불러오기
    void import_schedule(List<Schedule> scList);

    Schedule update_schedule(Long scid, UpdateScheduleRequest request);

    void delete_schedule(Long scid);


    /* 검색 관련 */
//    디테일 검색
    Optional<Schedule> search_schedule(Long schedule_id);

    // 목록 검색
    List<Map<String, String>> search_all_marker();
//    전체 검색
    Collection<Schedule> searchAll_schedule();
//    날짜 단위
    Optional<Schedule> findByDate(LocalDateTime start_time, LocalDateTime end_time);
//    강의 단위 검색
//    강사 단위 검색
}
