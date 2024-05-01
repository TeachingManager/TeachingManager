package com.TeachingManager.TeachingManager.Service.Schedule;


import com.TeachingManager.TeachingManager.domain.Schedule;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScheduleService {
    // 내부에서 scid 를 만든다.
    void create_schedule(Schedule sc);

    //    강의 불러오기
    void import_schedule(List<Schedule> scList);

    void update_schedule(Schedule sc);

    void delete_schedule(Long scid);


    /* 검색 관련 */
//    디테일 검색
    Schedule search_schedule(Long schedule_id);

    // 목록 검색
    List<Map<String, String>> search_all_marker();
//    전체 검색
    Collection<Schedule> searchAll_schedule();
//    날짜 단위
    Optional<Schedule> findByDate(LocalDateTime start_time, LocalDateTime end_time);
//    강의 단위 검색
//    강사 단위 검색
}
