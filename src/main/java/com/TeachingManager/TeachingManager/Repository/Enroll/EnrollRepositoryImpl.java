package com.TeachingManager.TeachingManager.Repository.Enroll;


import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledStudentsResponse;
import com.TeachingManager.TeachingManager.DTO.Enroll.Response.NotEnrolledLecturesResponse;
import com.TeachingManager.TeachingManager.DTO.Fee.EnrollFeeResponse;
import com.TeachingManager.TeachingManager.domain.Enroll;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Component
@RequiredArgsConstructor
public class EnrollRepositoryImpl implements EnrollRepository{
    private final EntityManager em;

    //////////////////////////////////////////////////////////
    ///                       조회                           //
    //////////////////////////////////////////////////////////
    
    // 특정 달의 특정 강의를 등록한 학생들 리스트 반환
    @Override
    public List<EnrolledStudentsResponse> findEnrolledStudentsByDate(Long institute_id, Long lecture_id, Short year, Short month) {
        return em.createQuery(
                "SELECT new com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledStudentsResponse(en.lecture.lecture_id, en.student.id, " +
                        "en.student.name, en.year, en.month, en.payed_fee," +
                        " en.fullPayment, en.lecture.fee) " +
                        "FROM Enroll en " +
                        "WHERE en.lecture.institute.pk = : instituteId " +
                        "AND en.lecture.lecture_id = :lectureId " +
                        "AND en.year = :year " +
                        "AND en.month = :month", EnrolledStudentsResponse.class
        ).setParameter("instituteId", institute_id)
        .setParameter("lectureId", lecture_id)
        .setParameter("year", year)
        .setParameter("month", month)
        .getResultList();
    }

    // 특정달에 개설된 강의 리스트 반환
    @Override
    public List<EnrolledLecturesResponse> findEnrolledLecturesByDate(Long institute_id, Short year, Short month) {
        return em.createQuery(
                        "SELECT new com.TeachingManager.TeachingManager.DTO.Enroll.Response.EnrolledLecturesResponse(lec.lecture_id, lec.name, :year, :month, lec.fee) " +
                                "FROM Lecture lec " +
                                "WHERE lec.institute.pk = :instituteId " +
                                "AND lec.lecture_id IN ( SELECT en.lecture.lecture_id " +
                                                            "FROM Enroll en " +
                                                            "WHERE en.lecture.institute.pk = :instituteId " +
                                                            "AND en.year = :year " +
                                                            "AND en.month = :month)"
                        , EnrolledLecturesResponse.class
                ).setParameter("instituteId", institute_id)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();
    }

    // 특정 달에 개설되지 않은 강의 리스트 반환
    @Override
    public List<NotEnrolledLecturesResponse> findNotEnrolledLecturesByDate(Long institute_id, Short year, Short month) {
        return em.createQuery(
                        "SELECT new com.TeachingManager.TeachingManager.DTO.Enroll.Response.NotEnrolledLecturesResponse(lec.lecture_id, lec.name) " +
                                "FROM Lecture lec " +
                                "WHERE lec.institute.pk = :instituteId " +
                                "AND lec.lecture_id NOT IN ( SELECT en.lecture.lecture_id " +
                                                                    "FROM Enroll en " +
                                                                    "WHERE en.lecture.institute.pk = :instituteId " +
                                                                    "AND en.year = :year " +
                                                                    "AND en.month = :month)"
                        , NotEnrolledLecturesResponse.class
                ).setParameter("instituteId", institute_id)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();
    }

    @Override
    public Optional<Enroll> findById(Long institute_id, Long enroll_id) {
        return em.createQuery("SELECT en " +
                "FROM Enroll en " +
                "WHERE en.lecture.institute.pk  = :instituteId " +
                "AND en.enroll_id = :enrollId", Enroll.class)
                .setParameter("instituteId", institute_id)
                .setParameter("enrollId", enroll_id)
                .getResultStream().findFirst();
    }

    // 해당 달의 수강료/ 수강료 납부 일정 가져오기
    @Override
    public List<EnrollFeeResponse> findEnrolledFeeByDate(Long institute_id, Short year, Short month) {
        return em.createQuery(
                        "SELECT new com.TeachingManager.TeachingManager.DTO.Fee.EnrollFeeResponse(en.student.name, en.student.id, en.lecture.lecture_id, en.lecture.name, en.lecture.fee, en.lecture.teacher.name, en.payed_fee, en.fullPayment, en.enroll_id)" +
                                "FROM Enroll en " +
                                "WHERE en.lecture.institute.pk  = :instituteId " +
                                "AND en.year = : year " +
                                "AND en.month = :month"
                        , EnrollFeeResponse.class
                ).setParameter("instituteId", institute_id)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();
    }
    
    

    //////////////////////////////////////////////////////////
    ///                       저장                           //
    //////////////////////////////////////////////////////////
    @Override
    @Transactional
    public Enroll save(Enroll enroll) {
        em.persist(enroll);
        return enroll;
    }

    //////////////////////////////////////////////////////////
    ///                       삭제                           //
    //////////////////////////////////////////////////////////
    @Override
    @Transactional
    public String delete(Long institute_id, Long enroll_id) {
        int deleteCount =
                em.createQuery(
            "DELETE FROM Enroll en" +
                " WHERE en.lecture.institute.pk = :instituteId " +
                    "AND en.enroll_id = :enrollId")
                        .setParameter("instituteId", institute_id)
                        .setParameter("enrollId", enroll_id)
                        .executeUpdate();

        if (deleteCount > 0){
            return "삭제 됨";
        }
        return "해당하는 튜플이 없음! 오류!";
    }
}
