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
import java.util.Objects;
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
    public String delete(Long institute_pk, Long attend_id) {
        Attend attend = em.find(Attend.class, attend_id);
        // 조인을 하는 것보다 차라리 select 연산 을 두 번 하는게 더 저렴해보인다.
        if (attend != null && Objects.equals(attend.getSchedule().getInstitute().getPk(), institute_pk)){
            em.remove(attend);
            return "제거 성공함.";
        }
        return "존재하지 않거나, 자격이 없는 학원에 요청보냈음";
    }

    @Override
    public Optional<Attend> searchById(Long institute_id, Long attend_id) {
        return em.createQuery(
                        "SELECT a " +
                            "FROM Attend a " +
                            "WHERE a.id = :attendId " +
                                "AND a.student.institute.id = :instituteId", Attend.class)
                .setParameter("attendId", attend_id)
                .setParameter("instituteId", institute_id)
                .getResultStream().findFirst();
    }

    @Override
    public List<Attend> searchAttendanceByScheduleId(Long institute_id, Long schedule_id) {
        return em.createQuery(
                "select at " +
                    "from Attend at " +
                    "where at.schedule_id = :schedule_id" +
                        "AND at.student.institute.id = :instituteId"
                        , Attend.class
        ).setParameter("schedule_id", schedule_id)
                .setParameter("institute_id", institute_id)
                .getResultList();
    }

    // 특정 학생의 특정 스케쥴에 대한 출석 정보 가져오기
    @Override
    public Optional<Attend> searchOneByStudentId(Long institute_id, Long schedule_id, Long student_id) {
        Attend attend =  em.createQuery(
                "select at " +
                        "from Attend at " +
                        "where at.schedule_id = :schedule_id " +
                            "AND at.student.institute.id = :instituteId" +
                            "AND student_id = :student_id", Attend.class
        )
                .setParameter("schedule_id", schedule_id)
                .setParameter("institute_id", institute_id)
                .setParameter("student_id", student_id).getSingleResult();
        return Optional.ofNullable(attend);
    }

    // 학생의 특정 달의 모든 강의에 대한  출석
    @Override
    public List<StudentsMonthAttendRecord> searchMonthlyAttendanceByStudentId(Long institute_id, Long student_id, LocalDate date_info) {
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        return em.createQuery(
                        "SELECT new com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.StudentsMonthAttendRecord(at.attend_id,lec.lecture_id,lec.name, at.attendance, sc.start_date)" +
                            "FROM Attend at " +
                            "INNER JOIN at.schedule sc " +
                            "INNER JOIN sc.lecture lec " +
                            "WHERE at.student_id = :student_id " +
                                "AND sc.institute_id = :institute_id " +
                                "AND sc.start_date <= :endOfMonth " +
                                "AND sc.end_date >= :startOfMonth " +
                            "ORDER BY sc.lecture_id ASC, sc.start_date ASC", // 강의 순서대로, 동일 강의에선 시작날짜 순서대로
                        StudentsMonthAttendRecord.class
                ).setParameter("student_id", student_id)
                .setParameter("institute_id", institute_id)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .getResultList();
    }

    // 특정 강의의 모든 학생들의 특정달의 출석
    @Override
    public List<LectureMonthAttendanceRecord> searchMonthlyAttendanceByLectureId(Long institute_id, Long lecture_id, LocalDate date_info) {
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        return em.createQuery(
                        "SELECT new com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.LectureMonthAttendanceRecord(at.attend_id, st.id, st.name, at.attendance, sc.start_date) " +
                                "FROM Attend at " +
                                "INNER JOIN at.schedule sc " +
                                "INNER JOIN at.student st " +
                                "WHERE sc.lecture_id = :lecture_id " +
                                    "AND sc.institute_id = :institute_id " +
                                    "AND sc.start_date <= :endOfMonth " +
                                    "AND sc.end_date >= :startOfMonth " +
                                "ORDER BY at.student_name ASC, sc.start_date ASC", // 학생 순서대로, 동일학생이면 시작날짜 순서대로.
                        LectureMonthAttendanceRecord.class
                ).setParameter("lecture_id", lecture_id)
                .setParameter("institute_id", institute_id)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .getResultList();
    }
    
// 특정 강의의 특정 학생의 특정 달의 출석
    @Override
    public List<StudentMonthLectureAttendRecord> searchMonthlyAttendanceByLectureAndStudentId(Long institute_id, Long lecture_id, Long student_id, LocalDate date_info) {
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        return em.createQuery(
                        "SELECT at.student_id, lec.lecture_id, at.attendance, sc.start_date" +
                                "FROM Attend at " +
                                "INNER JOIN at.schedule sc " +
                                "INNER JOIN sc.lecture lec " +
                                "WHERE at.student_id = :student_id " +
                                    "AND sc.institute_id = :institute_id " +
                                    "AND sc.lecture_id = :lecture_id " +
                                    "AND sc.start_date <= :endOfMonth " +
                                    "AND sc.end_date >= :startOfMonth " +
                                "ORDER BY sc.start_date ASC", // 날짜 순서대로
                        StudentMonthLectureAttendRecord.class
                ).setParameter("student_id", student_id)
                .setParameter("lecture_id", lecture_id)
                .setParameter("institute_id", institute_id)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .getResultList();
    }
}
