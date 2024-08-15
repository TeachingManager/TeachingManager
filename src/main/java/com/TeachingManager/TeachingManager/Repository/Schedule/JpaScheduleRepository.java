package com.TeachingManager.TeachingManager.Repository.Schedule;

import com.TeachingManager.TeachingManager.DTO.Schedule.ScheduleResponse;
import com.TeachingManager.TeachingManager.domain.Schedule;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @Transactional
    public Schedule save(Schedule sc) {
        em.persist(sc);
        return sc;
    }

    @Override
    @Transactional
    public String delete(Long institute_id, Long scid) {
        int deleteCount = em.createQuery("DELETE FROM Schedule sc " +
                        "WHERE sc.institute.pk = :instituteId  " +
                            "AND sc.schedule_id = :scheduleId")
                .setParameter("instituteId", institute_id)
                .setParameter("scheduleId", scid)
                .executeUpdate();;

        if(deleteCount == 0) {
            return "알맞는 대상이 없었음";
        }
        return "삭제완료";
    }

    @Override
    public String deleteByLectureDate(Long institute_id, Long lecture_id, LocalDate date_info) {
        // 시작일과 끝날짜
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        int deleteCount = em.createQuery(
                "DELETE FROM Schedule sc " +
                        "WHERE sc.institute.pk = :instituteId  " +
                        "AND sc.lecture.lecture_id = :lectureId " +
                        "AND sc.start_date <= :endOfMonth " +
                        "AND sc.end_date >= :startOfMonth")
                .setParameter("instituteId", institute_id)
                .setParameter("lectureId", lecture_id)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .executeUpdate();;

        if(deleteCount == 0) {
            return "알맞는 대상이 없었음";
        }
        return "삭제완료";
    }

    @Override
    public Optional<Schedule> searchById(Long institute_id, Long scid) {
        System.out.println("institute_id = " + institute_id);
        System.out.println("scid = " + scid);
        return em.createQuery(
                        "SELECT sc " +
                                "FROM Schedule sc " +
                                "WHERE sc.institute.pk = :instituteId " +
                                "AND sc.schedule_id = :scheduleId", Schedule.class)
                .setParameter("scheduleId", scid)
                .setParameter("instituteId", institute_id)
                .getResultStream().findFirst();
    }

    // 학원의 전체 일정
    @Override
    public Set<ScheduleResponse> search_all(Long institute_id) {
        return em.createQuery(
                        "SELECT new com.TeachingManager.TeachingManager.DTO.Schedule.ScheduleResponse(sc.schedule_id, sc.title, sc.start_date, sc.end_date, sc.memo," +
                                "COALESCE(lec.lecture_id, 0L), COALESCE(lec.name, :defaultLectureName))" +
                                "FROM Schedule sc " +
                                "LEFT JOIN sc.lecture lec " + // 이걸 추가해야, lecture 가 null 인 경우도 추가할 수 있다는 듯 하다.
                                "WHERE sc.institute.pk = :institute_id ", ScheduleResponse.class)
                .setParameter("defaultLectureName", "기본일정")
                .setParameter("institute_id", institute_id)
                .getResultStream() // Stream<Schedule> 반환
                .collect(Collectors.toSet()); // Stream을 Set으로 변환
    }

    // 학원의 특정 달의 일정
    @Override
    public Set<ScheduleResponse> filter_by_date(Long institute_id, LocalDate date_info) {

        // 시작일과 끝날짜
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        return em.createQuery(
                        "SELECT new com.TeachingManager.TeachingManager.DTO.Schedule.ScheduleResponse(sc.schedule_id, sc.title, sc.start_date, sc.end_date, sc.memo," +
                                "COALESCE(lec.lecture_id, 0L), COALESCE(lec.name, :defaultLectureName))" +
                                "FROM Schedule sc " +
                                "LEFT JOIN sc.lecture lec " + // 이걸 추가해야, lecture 가 null 인 경우도 추가할 수 있다는 듯 하다.
                                "WHERE sc.institute.pk = :institute_id " +
                                "AND sc.start_date <= :endOfMonth " +
                                "AND sc.end_date >= :startOfMonth", ScheduleResponse.class)
                .setParameter("defaultLectureName", "기본일정")
                .setParameter("institute_id", institute_id)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .getResultStream() // Stream<Schedule> 반환
                .collect(Collectors.toSet()); // Stream을 Set으로 변환
    }

    // 특정강의의 이번달 일정 가져오는 함수.
    @Override
    public Set<Schedule> filter_by_lecture(Long institute_id, Long lecture_id, LocalDate date_info) {
        // 시작일과 끝날짜
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        return em.createQuery(
                        "SELECT sc " +
                                "FROM Schedule sc " +
                                "WHERE sc.institute.pk = :institute_id " +
                                "AND sc.lecture.lecture_id = :lectureId " +
                                "AND sc.start_date <= :endOfMonth " +
                                "AND sc.end_date >= :startOfMonth", Schedule.class)
                .setParameter("institute_id", institute_id)
                .setParameter("lectureId", lecture_id)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .getResultStream() // Stream<Schedule> 반환
                .collect(Collectors.toSet()); // Stream을 Set으로 변환
    }

    // DTO 변환
    public ScheduleResponse convertToDTO(Schedule schedule) {
        return new ScheduleResponse(schedule);
    }
}
