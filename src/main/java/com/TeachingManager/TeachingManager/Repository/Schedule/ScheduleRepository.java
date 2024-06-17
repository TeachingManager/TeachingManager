package com.TeachingManager.TeachingManager.Repository.Schedule;


import com.TeachingManager.TeachingManager.domain.Schedule;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule save(Schedule sc);

    void delete(Long scid);

    Optional<Schedule> searchById(Long scid);
    Collection<Schedule> search_all(Long institute_id);

    Optional<Schedule> filter_by_date (Long institute_id, Date date_info);

}
