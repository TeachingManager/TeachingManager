package com.TeachingManager.TeachingManager.Repository.Schedule;

import com.TeachingManager.TeachingManager.DTO.Schedule.ScheduleInfo;
import com.TeachingManager.TeachingManager.domain.Schedule;
import jakarta.persistence.EntityManager;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Component
public class JpaScheduleRepository  implements ScheduleRepository{

    private final EntityManager em;

    public JpaScheduleRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Schedule save(Schedule sc) {
        em.persist(sc);
        return sc;
    }

    @Override
    public void delete(Long scid) {
        Schedule sc = em.find(Schedule.class, scid);
        if (sc != null) {
            em.remove(sc);
        }
    }

    @Override
    public Optional<Schedule> searchById(Long scid) {
        Schedule sc =  em.find(Schedule.class, scid); //pk 라 가능
        return Optional.ofNullable(sc);
    }

    @Override
    public Set<ScheduleInfo> search_all(Long institute_id) {
        Set<Schedule> scheduleSet = em.createQuery(
                        "select sc from Schedule sc where sc.institute.id = :institute_id", Schedule.class)
                .setParameter("institute_id", institute_id)
                .getResultStream() // Stream<Schedule> 반환
                .collect(Collectors.toSet()); // Stream을 Set으로 변환


        return scheduleSet.stream()
                    .map(this::convertToDTO) // convertToDTO 메서드를 사용하여 변환
                    .collect(Collectors.toSet());
    }

    @Override
    public Set<ScheduleInfo> filter_by_date(Long institute_id, LocalDate date_info) {

        // 시작일과 끝날짜
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        Set<Schedule> scheduleSet = em.createQuery(
                        "SELECT sc FROM Schedule sc WHERE sc.institute.id = :institute_id AND sc.start_date <= :endOfMonth AND sc.end_date >= :startOfMonth", Schedule.class)
                .setParameter("institute_id", institute_id)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .getResultStream() // Stream<Schedule> 반환
                .collect(Collectors.toSet()); // Stream을 Set으로 변환

        return scheduleSet.stream()
                .map(this::convertToDTO) // convertToDTO 메서드를 사용하여 변환
                .collect(Collectors.toSet());
    }

    // DTO 변환
    public ScheduleInfo convertToDTO(Schedule schedule) {
        return new ScheduleInfo(schedule.getSchedule_id(), schedule.getTitle(), schedule.getStart_date(), schedule.getEnd_date(), schedule.getMemo(),schedule.getInstitute().getPk());
    }
}
