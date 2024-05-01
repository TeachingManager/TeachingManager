package com.TeachingManager.TeachingManager.Service.Schedule;

import com.TeachingManager.TeachingManager.Repository.Schedule.ScheduleRepository;
import com.TeachingManager.TeachingManager.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class ScheduleServiceImpl  implements  ScheduleService{

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

//    새 스케쥴 생성
    @Override
    public void create_schedule(Schedule sc) {
        scheduleRepository.save(sc);
    }

//    강의를 스케쥴에 추가
    @Override
    public void import_schedule(List<Schedule> scList) {
        scList.forEach(scheduleRepository::save);
    }

//   스케쥴 수정
    @Override
    public void update_schedule(Schedule sc) {
        scheduleRepository.update(sc);
    }

//    스케쥴 삭제
    @Override
    public void delete_schedule(Long scid) {
        scheduleRepository.delete(scid);
    }

//    단일 스케쥴 검색
    @Override
    public Schedule search_schedule(Long schedule_id) {
        return scheduleRepository.search_one(schedule_id);
    }
    
//    스케줄 제목, (날짜 등 추후 추가 예정) 만 뽑아오기
    @Override
    public List<Map<String, String>> search_all_marker() {

        Collection<Schedule> scList = scheduleRepository.search_all();
        List<Map<String, String>> schedules = new ArrayList<>();
        Map<String, String> temp_sc = new HashMap<>();

        scList.forEach(schedule -> {
            temp_sc.put("title", schedule.getTitle());

            schedules.add(temp_sc);
        });

        return schedules;
    }

    //    스케쥴 전체 검색
    @Override
    public Collection<Schedule> searchAll_schedule() {
        return scheduleRepository.search_all();
    }

//    스케쥴 날짜로 검색
    @Override
    public Optional<Schedule> findByDate(LocalDateTime start_time, LocalDateTime end_time) {
        return scheduleRepository.filter_by_date(start_time, end_time);
    }
}
