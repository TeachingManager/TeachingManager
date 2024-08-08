package com.TeachingManager.TeachingManager.Service.Schedule;

import com.TeachingManager.TeachingManager.DTO.Schedule.AddScheduleRequest;
import com.TeachingManager.TeachingManager.DTO.Schedule.UpdateScheduleRequest;
import com.TeachingManager.TeachingManager.Repository.Schedule.ScheduleRepository;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl  implements  ScheduleService{

    private final ScheduleRepository scheduleRepo;
    private final InstituteRepository instituteRepo;


    //    새 스케쥴 생성
    @Override
    @Transactional
    public Schedule create_schedule(AddScheduleRequest request, String email) {
        Optional<Institute> institute = instituteRepo.findByEmail(email);
        System.out.println("create_schedule의 institute = " + institute);
        if (institute.isPresent()){
            return scheduleRepo.save(request.toEntity(institute.get()));
        }
        else {
            System.out.println("존재하지 않는 학원에서의 요청입니다.");
            return null;
        }
    }



//    강의를 스케쥴에 추가
    @Override
    public void import_schedule(List<Schedule> scList) {
        scList.forEach(scheduleRepo::save);
    }


//   스케쥴 수정
    @Override
    @Transactional
    public Schedule update_schedule(Long scid, UpdateScheduleRequest request) {
        Schedule sc = scheduleRepo.searchById(scid).orElseThrow(() -> new IllegalArgumentException("not found : " + scid));
        sc.update(request.getTitle(), request.getStart_date(), request.getEnd_date(), request.getMemo());
        return sc;
    }


//    스케쥴 삭제
    @Override
    @Transactional
    public void delete_schedule(Long scid) {
        scheduleRepo.delete(scid);
    }


//    단일 스케쥴 검색
    @Override
    @Transactional
    public Optional<Schedule> search_schedule(Long schedule_id) {
        return scheduleRepo.searchById(schedule_id);
    }

    
//    스케줄 제목, (날짜 등 추후 추가 예정) 만 뽑아오기
    @Override
    public List<Map<String, String>> search_all_marker(Long institute_id) {

        Collection<Schedule> scList = scheduleRepo.search_all(institute_id);
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
        return scheduleRepo.search_all(institute_id);
    }

//    스케쥴 날짜로 검색
    @Override
    public Optional<Schedule> findByDate(Long institute_id, Date date_info) {
        return scheduleRepo.filter_by_date(institute_id, date_info);
    }
}
