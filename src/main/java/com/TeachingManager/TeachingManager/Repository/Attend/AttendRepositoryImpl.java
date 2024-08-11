package com.TeachingManager.TeachingManager.Repository.Attend;

import com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.LectureMonthAttendanceRecord;
import com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.StudentMonthLectureAttendRecord;
import com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.StudentsMonthAttendRecord;
import com.TeachingManager.TeachingManager.domain.Attend;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Component
@RequiredArgsConstructor
public class AttendRepositoryImpl implements AttendRepository{

    private final EntityManager em;

    @Override
    public Attend save(Attend attend) {
        em.persist(attend);
        return attend;
    }

    @Override
    public void delete(Long attend_id) {
        Attend attend = em.find(Attend.class, attend_id);
        if (attend != null ){
            em.remove(attend);
        }

    }

    @Override
    public Optional<Attend> searchById(Long attend_id) {
        Attend attend =  em.find(Attend.class, attend_id); //pk 라 가능
        return Optional.ofNullable(attend);
    }

    @Override
    public List<Attend> searchAttendanceByScheduleId(Long schedule_id) {
        return em.createQuery(
                "select at from Attend at where at.schedule_id = :schedule_id", Attend.class
        ).setParameter("schedule_id", schedule_id).getResultList();
    }

    // 특정 학생의 특정 스케쥴에 대한 출석 정보 가져오기
    @Override
    public Optional<Attend> searchOneByStudentId(Long schedule_id, Long student_id) {
        Attend attend =  em.createQuery(
                "select at from Attend at where at.schedule_id = :schedule_id AND student_id = :student_id", Attend.class
        ).setParameter("schedule_id", schedule_id)
                .setParameter("student_id", student_id).getSingleResult();
        return Optional.ofNullable(attend);
    }

    // 학생의 특정 달의 모든 강의에 대한  출석
    @Override
    public List<StudentsMonthAttendRecord> searchMonthlyAttendanceByStudentId(Long student_id, LocalDate date_info) {
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        return em.createQuery(
//                        "SELECT new com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.StudentsMonthAttendRecord(at.student_id, lec.name, at.attendance, sc.start_date)" +
                        "SELECT new com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.StudentsMonthAttendRecord(at.attend_id,at.student_id, at.attendance, sc.start_date)" +
                            "FROM Attend at " +
                            "INNER JOIN at.schedule sc " +
//                            "INNER JOIN sc.lecture lec " +
                            "WHERE at.student_id = :student_id " +
                                "AND sc.start_date <= :endOfMonth " +
                                "AND sc.end_date >= :startOfMonth " +
                            "ORDER BY sc.start_date ASC",
//                            "ORDER BY sc.lecture_id ASC, sc.start_date ASC", // 강의 순서대로, 동일 강의에선 시작날짜 순서대로
                        StudentsMonthAttendRecord.class
                ).setParameter("student_id", student_id)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .getResultList();
    }

    // 특정 강의의 모든 학생들의 특정달의 출석
    @Override
    public List<LectureMonthAttendanceRecord> searchMonthlyAttendanceByLectureId(Long lecture_id, LocalDate date_info) {
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        return em.createQuery(
                        "SELECT new com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.LectureMonthAttendanceRecord(at.attend_id, st.id, st.name, at.attendance, sc.start_date) " +
                                "FROM Attend at " +
                                "INNER JOIN at.schedule sc " +
                                "INNER JOIN at.student st " +
                                "WHERE sc.lecture_id = :lecture_id " +
                                    "AND sc.start_date <= :endOfMonth " +
                                    "AND sc.end_date >= :startOfMonth " +
                                "ORDER BY at.student_id ASC, sc.start_date ASC", // 학생 순서대로, 동일학생이면 시작날짜 순서대로.
                        LectureMonthAttendanceRecord.class
                ).setParameter("lecture_id", lecture_id)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .getResultList();
    }
    
// 특정 강의의 특정 학생의 특정 달의 출석
    @Override
    public List<StudentMonthLectureAttendRecord> searchMonthlyAttendanceByLectureAndStudentId(Long lecture_id, Long student_id, LocalDate date_info) {
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        return em.createQuery(
                        "SELECT at.student_id, lec.lecture_id, at.attendance, sc.start_date" +
                                "FROM Attend at " +
                                "INNER JOIN at.schedule sc " +
                                "INNER JOIN sc.lecture lec " +
                                "WHERE at.student_id = :student_id " +
                                    "AND sc.lecture_id = :lecture_id " +
                                    "AND sc.start_date <= :endOfMonth " +
                                    "AND sc.end_date >= :startOfMonth " +
                                "ORDER BY sc.start_date ASC", // 날짜 순서대로
                        StudentMonthLectureAttendRecord.class
                ).setParameter("student_id", student_id)
                .setParameter("lecture_id", lecture_id)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .getResultList();
    }
}
