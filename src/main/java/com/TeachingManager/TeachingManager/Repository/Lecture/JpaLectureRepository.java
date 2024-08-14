package com.TeachingManager.TeachingManager.Repository.Lecture;

import com.TeachingManager.TeachingManager.domain.Lecture;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Optional<Lecture> findOneById(Long instituteId, Long lectureId) {
        return em.createQuery("select l from Lecture where l.institute.pk = :instituteId and l.lecture_id = :lectureId", Lecture.class)
                .setParameter("instituteId", instituteId).setParameter("lectureId", lectureId)
                .getResultStream().findFirst();
    }

    @Override
    public void delete(Long instituteId, Long lectureId) {
        em.createQuery("delete from Lecture l where l.institute.pk = :instituteId and l.lecture_id = :lectureId")
                .setParameter("instituteId", instituteId)
                .setParameter("lectureId", lectureId)
                .executeUpdate();
    }

    @Override
    public List<Lecture> findAll(Long instituteId) {
        return em.createQuery("select l from Lecture l where l.institute.pk = :instituteId", Lecture.class)
                .setParameter("instituteId", instituteId)
                .getResultList();
    }
}
