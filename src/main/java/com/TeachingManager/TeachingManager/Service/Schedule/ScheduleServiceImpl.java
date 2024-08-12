package com.TeachingManager.TeachingManager.Service.Schedule;

import com.TeachingManager.TeachingManager.DTO.Schedule.AddScheduleRequest;
import com.TeachingManager.TeachingManager.DTO.Schedule.MonthScheduleResponse;
import com.TeachingManager.TeachingManager.DTO.Schedule.ScheduleInfo;
import com.TeachingManager.TeachingManager.DTO.Schedule.UpdateScheduleRequest;
import com.TeachingManager.TeachingManager.Repository.Schedule.ScheduleRepository;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    public Schedule create_schedule(AddScheduleRequest request, CustomUser user) {
        Optional<Institute> institute = instituteRepo.findByPk(user.getPk());
        if (institute.isPresent()){
            return scheduleRepo.save(request.toEntity(institute.get()));
        }
        else {
            System.out.println("존재하지 않는 학원에서의 요청입니다.");
            return null;
        }
    }


//   스케쥴 수정
    @Override
    @Transactional
    public Schedule update_schedule(CustomUser user, Long scid, UpdateScheduleRequest request) {
        Optional<Schedule> sc = scheduleRepo.searchById(user.getPk(), scid);
        if(sc.isPresent()){
            sc.get().update(request.getTitle(), request.getStart_date(), request.getEnd_date(), request.getMemo());
            scheduleRepo.save(sc.get());
            return sc.get();
        } // 학생의 소속
        else{
            throw new RuntimeException("올바르지 않은 접근입니다.");
        }
    }


//    스케쥴 삭제
    @Override
    @Transactional
    public void delete_schedule(CustomUser user, Long scid) {
        scheduleRepo.delete(user.getPk(),scid);
    }


//    단일 스케쥴 검색
    @Override
    @Transactional
    public Schedule search_schedule(CustomUser user, Long schedule_id) {

        // 선생님이 접근시
        if(user instanceof Teacher) {
            Optional<Schedule> schedule = scheduleRepo.searchById(((Teacher) user).getInstitutePk(),schedule_id);
            if (schedule.isPresent()) {
                return schedule.get();
            } else {
                throw new RuntimeException("강사->스케줄의 올바르지 않은 접근입니다.");
            }
        }
        // 학원이 접근시
        else{
            Optional<Schedule> schedule = scheduleRepo.searchById(user.getPk(),schedule_id);
            if (schedule.isPresent()) {
                return schedule.get();
            } else {
                throw new RuntimeException("학원->스케쥴의 올바르지 않은 접근입니다.");
            }
        }
    }


    //    스케쥴 월별로 검색
    @Override
    public MonthScheduleResponse searchAll_scheduleByDate(CustomUser user, LocalDate date_info) {
        // 유저의 권한을 확인하여, PRESIDENT 면 자기 pk , TEACHER 라면 외래키를 institute_id 에 저장
        Long institute_id;

        if (user instanceof Teacher){
            institute_id = ((Teacher) user).getInstitutePk();
        } else {
            institute_id = user.getPk();
        }

        return new MonthScheduleResponse(scheduleRepo.filter_by_date(institute_id, date_info));

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    
//    스케줄 제목, (날짜 등 추후 추가 예정) 만 뽑아오기
    @Override
    public List<Map<String, String>> search_all_marker(Long institute_id) {

        Collection<ScheduleInfo> scList = scheduleRepo.search_all(institute_id);
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
    public MonthScheduleResponse searchAll_schedule(CustomUser user) {
        // 유저의 권한을 확인하여, PRESIDENT 면 자기 pk , TEACHER 라면 외래키를 institute_id 에 저장
        Long institute_id;

        if (user.getAuthorities().contains(new SimpleGrantedAuthority("PRESIDENT"))){
            institute_id = user.getPk();
        }
        else if (user.getAuthorities().contains(new SimpleGrantedAuthority("TEACHER"))){
            Teacher teacher = (Teacher) user;
            institute_id = teacher.getInstitute() != null ? teacher.getInstitutePk() : null; // Institute의 ID 가져오기
        } else {
            institute_id = null;
        }
        return new MonthScheduleResponse(scheduleRepo.search_all(institute_id));
    }

}
