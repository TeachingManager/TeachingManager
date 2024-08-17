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
import java.util.UUID;

@Transactional
@Component
@RequiredArgsConstructor
public class AttendRepositoryImpl implements AttendRepository{

    private final EntityManager em;

    @Override
    @Transactional
    public Attend save(Attend attend) {
        em.persist(attend);
        return attend;
    }

    @Override
    @Transactional
    public String delete(UUID institute_pk, Long attend_id) {
        Attend attend = em.find(Attend.class, attend_id);
        // 조인을 하는 것보다 차라리 select 연산 을 두 번 하는게 더 저렴해보인다.
        if (attend != null && Objects.equals(attend.getSchedule().getInstitute().getPk(), institute_pk)){
            em.remove(attend);
            return "제거 성공함.";
        }
        return "존재하지 않거나, 자격이 없는 학원에 요청보냈음";
    }

    @Override
    public String deleteMonthAttend(UUID institute_id, Long lecture_id, Long student_id, LocalDate date_info) {
        // 시작일과 끝날짜
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        int deleteCount =
                em.createQuery("DELETE FROM Attend at " +
                                " WHERE at.student.id = :studentId " +
                                "AND at.schedule.schedule_id IN (SELECT sc.schedule_id " +
                                                                    "FROM Schedule sc " +
                                                                    "WHERE sc.institute.pk = :instituteId " +
                                                                        "AND sc.lecture.lecture_id = :lectureId " +
                                                                        "AND sc.start_date <= :endOfMonth " +
                                                                        "AND sc.end_date >= :startOfMonth) ")
                        .setParameter("instituteId", institute_id)
                        .setParameter("lectureId", lecture_id)
                        .setParameter("studentId", student_id)
                        .setParameter("startOfMonth", startOfMonth)
                        .setParameter("endOfMonth", endOfMonth)
                        .executeUpdate();

        if (deleteCount > 0){
            return "삭제 됨";
        }
        return "해당하는 튜플이 없음! 오류!";
    }

    @Override
    public Optional<Attend> searchById(UUID institute_id, Long attend_id) {
        return em.createQuery(
                        "SELECT a " +
                            "FROM Attend a " +
                            "WHERE a.attend_id = :attendId " +
                                "AND a.student.institute.pk = :instituteId", Attend.class)
                .setParameter("attendId", attend_id)
                .setParameter("instituteId", institute_id)
                .getResultStream().findFirst();
    }

    @Override
    public List<Attend> searchAttendanceByScheduleId(UUID institute_id, Long schedule_id) {
        return em.createQuery(
                "select at " +
                    "from Attend at " +
                    "where at.schedule_id = :schedule_id" +
                        "AND at.student.institute.pk = :instituteId"
                        , Attend.class
        ).setParameter("schedule_id", schedule_id)
                .setParameter("institute_id", institute_id)
                .getResultList();
    }

    // 특정 학생의 특정 스케쥴에 대한 출석 정보 가져오기
    @Override
    public Optional<Attend> searchOneByStudentId(UUID institute_id, Long schedule_id, Long student_id) {
        Attend attend =  em.createQuery(
                "select at " +
                        "from Attend at " +
                        "where at.schedule.schedule_id = :schedule_id " +
                            "AND at.student.institute.pk = :instituteId" +
                            "AND at.student.id = :student_id", Attend.class
        )
                .setParameter("schedule_id", schedule_id)
                .setParameter("institute_id", institute_id)
                .setParameter("student_id", student_id).getSingleResult();
        return Optional.ofNullable(attend);
    }

    // 학생의 특정 달의 모든 강의에 대한  출석
    @Override
    public List<StudentsMonthAttendRecord> searchMonthlyAttendanceByStudentId(UUID institute_id, Long student_id, LocalDate date_info) {
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        return em.createQuery(
                        "SELECT new com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.StudentsMonthAttendRecord(at.attend_id, at.schedule.lecture.lecture_id, at.schedule.lecture.name, at.attendance, at.schedule.start_date)" +
                            "FROM Attend at " +
                            "WHERE at.student.id = :student_id " +
                                "AND at.student.institute.pk = :institute_id " +
                                "AND at.schedule.start_date <= :endOfMonth " +
                                "AND at.schedule.end_date >= :startOfMonth " +
                            "ORDER BY at.schedule.lecture.lecture_id ASC, at.schedule.start_date ASC", // 강의 순서대로, 동일 강의에선 시작날짜 순서대로
                        StudentsMonthAttendRecord.class
                ).setParameter("student_id", student_id)
                .setParameter("institute_id", institute_id)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .getResultList();
    }

    // 특정 강의의 모든 학생들의 특정달의 출석
    @Override
    public List<LectureMonthAttendanceRecord> searchMonthlyAttendanceByLectureId(UUID institute_id, Long lecture_id, LocalDate date_info) {
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        return em.createQuery(
                        "SELECT new com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.LectureMonthAttendanceRecord(at.attend_id, st.id, st.name, at.attendance, sc.start_date) " +
                                "FROM Attend at " +
                                "INNER JOIN at.schedule sc " +
                                "INNER JOIN at.student st " +
                                "WHERE sc.lecture.lecture_id = :lecture_id " +
                                    "AND sc.institute.id = :institute_id " +
                                    "AND sc.start_date <= :endOfMonth " +
                                    "AND sc.end_date >= :startOfMonth " +
                                "ORDER BY at.student.name ASC, sc.start_date ASC", // 학생 순서대로, 동일학생이면 시작날짜 순서대로.
                        LectureMonthAttendanceRecord.class
                ).setParameter("lecture_id", lecture_id)
                .setParameter("institute_id", institute_id)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .getResultList();
    }
    
// 특정 강의의 특정 학생의 특정 달의 출석
    @Override
    public List<StudentMonthLectureAttendRecord> searchMonthlyAttendanceByLectureAndStudentId(UUID institute_id, Long lecture_id, Long student_id, LocalDate date_info) {
        LocalDateTime startOfMonth = date_info.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date_info.withDayOfMonth(date_info.lengthOfMonth()).atTime(LocalTime.MAX);

        return em.createQuery(
                        "SELECT at.student_id, sc.lecture.lecture_id, at.attendance, sc.start_date" +
                                "FROM Attend at " +
                                "INNER JOIN at.schedule sc " +
                                "WHERE at.student_id = :student_id " +
                                    "AND sc.institute_id = :institute_id " +
                                    "AND sc.lecture.lecture_id = :lecture_id " +
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
