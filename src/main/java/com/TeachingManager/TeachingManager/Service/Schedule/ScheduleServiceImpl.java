package com.TeachingManager.TeachingManager.Service.Schedule;

import com.TeachingManager.TeachingManager.DTO.Schedule.AddScheduleRequest;
import com.TeachingManager.TeachingManager.DTO.Schedule.UpdateScheduleRequest;
import com.TeachingManager.TeachingManager.Repository.Schedule.ScheduleRepository;
import com.TeachingManager.TeachingManager.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class ScheduleServiceImpl  implements  ScheduleService{

    ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    //    새 스케쥴 생성
    @Override
    @Transactional
    public Schedule create_schedule(AddScheduleRequest request) {
        return scheduleRepository.save(request.toEntity());
    }

//    강의를 스케쥴에 추가
    @Override
    public void import_schedule(List<Schedule> scList) {
        scList.forEach(scheduleRepository::save);
    }


//   스케쥴 수정
    @Override
    @Transactional
    public Schedule update_schedule(Long scid, UpdateScheduleRequest request) {
        Schedule sc = scheduleRepository.searchById(scid).orElseThrow(() -> new IllegalArgumentException("not found : " + scid));
        sc.update(request.getTitle(), request.getStart_date(), request.getEnd_date(), request.getMemo());
        return sc;
    }


//    스케쥴 삭제
    @Override
    @Transactional
    public void delete_schedule(Long scid) {
        scheduleRepository.delete(scid);
    }


//    단일 스케쥴 검색
    @Override
    @Transactional
    public Optional<Schedule> search_schedule(Long schedule_id) {
        return scheduleRepository.searchById(schedule_id);
    }

    
//    스케줄 제목, (날짜 등 추후 추가 예정) 만 뽑아오기
    @Override
    public List<Map<String, String>> search_all_marker(Long institute_id) {

        Collection<Schedule> scList = scheduleRepository.search_all(institute_id);
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
    public Collection<Schedule> searchAll_schedule(Long institute_id) {
        return scheduleRepository.search_all(institute_id);
    }

//    스케쥴 날짜로 검색
    @Override
    public Optional<Schedule> findByDate(Long institute_id, Date date_info) {
        return scheduleRepository.filter_by_date(institute_id, date_info);
    }
}
