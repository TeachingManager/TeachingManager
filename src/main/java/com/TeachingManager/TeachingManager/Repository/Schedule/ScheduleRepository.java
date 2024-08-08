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

    void delete(Long scid);

    Optional<Schedule> searchById(Long scid);
    Set<ScheduleInfo> search_all(Long institute_id);

    Set<ScheduleInfo> filter_by_date (Long institute_id, LocalDate date_info);

}
