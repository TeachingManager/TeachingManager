package com.TeachingManager.TeachingManager.Repository.Schedule;


import com.TeachingManager.TeachingManager.DTO.Schedule.ScheduleInfo;
import com.TeachingManager.TeachingManager.domain.Schedule;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface ScheduleRepository {
    Schedule save(Schedule sc);

    String delete(Long institute_id, Long scid);

    Optional<Schedule> searchById(Long institute_id, Long scid);
    Set<ScheduleInfo> search_all(Long institute_id);

    Set<ScheduleInfo> filter_by_date (Long institute_id, LocalDate date_info);

    // 특정 강의의 이번달 일정 가져오는 함수
    Set<Schedule> filter_by_lecture(Long institute_id, Long lecture_id, LocalDate date_info);


}
