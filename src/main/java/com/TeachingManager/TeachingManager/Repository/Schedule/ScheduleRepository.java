package com.TeachingManager.TeachingManager.Repository.Schedule;


import com.TeachingManager.TeachingManager.DTO.Schedule.ScheduleResponse;
import com.TeachingManager.TeachingManager.domain.Schedule;


import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ScheduleRepository {
    Schedule save(Schedule sc);

    String delete(UUID institute_id, Long scid);

    String deleteByLectureDate(UUID institute_id, Long lecture_id, LocalDate date_info);

    Optional<Schedule> searchById(UUID institute_id, Long scid);
    Set<ScheduleResponse> search_all(UUID institute_id);

    Set<ScheduleResponse> filter_by_date (UUID institute_id, LocalDate date_info);

    // 특정 강의의 이번달 일정 가져오는 함수
    Set<Schedule> filter_by_lecture(UUID institute_id, Long lecture_id, LocalDate date_info);


}
