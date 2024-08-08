package com.TeachingManager.TeachingManager.Service.Schedule;

import com.TeachingManager.TeachingManager.DTO.Schedule.AddScheduleRequest;
import com.TeachingManager.TeachingManager.DTO.Schedule.MonthScheduleResponse;
import com.TeachingManager.TeachingManager.DTO.Schedule.ScheduleInfo;
import com.TeachingManager.TeachingManager.DTO.Schedule.UpdateScheduleRequest;
import com.TeachingManager.TeachingManager.Repository.Schedule.ScheduleRepository;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Schedule;
import com.TeachingManager.TeachingManager.domain.Teacher;
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
    private final TeacherRepository teacherRepo;


    //    새 스케쥴 생성
    @Override
    @Transactional
    public Schedule create_schedule(AddScheduleRequest request, Long pk) {
        Optional<Institute> institute = instituteRepo.findByPk(pk);
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

//    스케쥴 월별로 검색
    @Override
    public MonthScheduleResponse searchAll_scheduleByDate(CustomUser user, LocalDate date_info) {
        // 유저의 권한을 확인하여, PRESIDENT 면 자기 pk , TEACHER 라면 외래키를 institute_id 에 저장
        Long institute_id;

        System.out.println("user.getAuthorities() = " + user.getAuthorities());
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))){
            institute_id = user.getPk();
        }
        else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEACHER"))){
            Teacher teacher = teacherRepo.findByPk(user.getPk()).get();
            institute_id = teacher.getInstitute() != null ? teacher.getInstitutePk() : null; // Institute의 ID 가져오기
        } else {
            institute_id = null;
        }

        return new MonthScheduleResponse(scheduleRepo.filter_by_date(institute_id, date_info));

    }
}
