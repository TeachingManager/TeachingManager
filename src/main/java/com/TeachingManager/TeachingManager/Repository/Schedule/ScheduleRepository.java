package com.TeachingManager.TeachingManager.Repository.Schedule;


import com.TeachingManager.TeachingManager.Forms.ScheduleForm;
import com.TeachingManager.TeachingManager.domain.Schedule;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface ScheduleRepository {
    void save(Schedule sc);

    void delete(Long scid);

    void update(Schedule sc);

    Schedule search_one(Long scid);
    Collection<Schedule> search_all();

    Optional<Schedule> filter_by_date (LocalDateTime start_time, LocalDateTime end_time);

}
