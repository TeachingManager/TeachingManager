package com.TeachingManager.TeachingManager.Repository.Schedule;

import com.TeachingManager.TeachingManager.Forms.ScheduleForm;
import com.TeachingManager.TeachingManager.domain.Schedule;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ScheduleRepositoryImpl implements ScheduleRepository {

    // 저장소
    private static Map<Long, Schedule> local_store = new HashMap<>();
    
    // 저장소에 저장
    @Override
    public void save(Schedule sc) {
        local_store.put(sc.getCid(), sc);
    }


    @Override
    public void delete(Long scid) {
        local_store.remove(scid);
    }

    @Override
    public void update(Schedule sc) {
        local_store.replace(sc.getSchedule_id(), sc);
    }

    @Override
    public Schedule search_one(Long scid) {
        return local_store.get(scid);
    }

    @Override
    public Collection<Schedule> search_all() {
        return local_store.values();
    }

    @Override
    public Optional<Schedule> filter_by_date ( LocalDateTime start_time, LocalDateTime end_time) {
        return Optional.empty();
    }
}
