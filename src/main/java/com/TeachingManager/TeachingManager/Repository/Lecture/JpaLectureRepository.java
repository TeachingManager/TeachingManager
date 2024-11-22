package com.TeachingManager.TeachingManager.Repository.Lecture;

import com.TeachingManager.TeachingManager.domain.Lecture;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaLectureRepository implements LectureRepository {

    private final EntityManager em;

    public JpaLectureRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Lecture save(Lecture lecture) {
        em.persist(lecture);
        return lecture;
    }

    @Override
    public Optional<Lecture> findOneById(UUID instituteId, Long lectureId) {
        return em.createQuery("select l from Lecture l where l.institute.pk = :instituteId and l.lecture_id = :lectureId", Lecture.class)
                .setParameter("instituteId", instituteId).setParameter("lectureId", lectureId)
                .getResultStream().findFirst();
    }

    @Override
    public void delete(UUID instituteId, Long lectureId) {
        em.createQuery("delete from Lecture l where l.institute.pk = :instituteId and l.lecture_id = :lectureId")
                .setParameter("instituteId", instituteId)
                .setParameter("lectureId", lectureId)
                .executeUpdate();
    }

    @Override
    public List<Lecture> findAll(UUID instituteId) {
        return em.createQuery("select l from Lecture l where l.institute.pk = :instituteId", Lecture.class)
                .setParameter("instituteId", instituteId)
                .getResultList();
    }

    @Override
    public List<Lecture> findAll_for_teacher(UUID instituteId, UUID teacherId) {
        return em.createQuery("select l from Lecture l where l.institute.pk = :instituteId AND l.teacher.pk = :teacherId", Lecture.class)
                .setParameter("instituteId", instituteId)
                .setParameter("teacherId", teacherId)
                .getResultList();
    }
}
