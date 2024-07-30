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
    public void save(Lecture lecture) {
        em.persist(lecture);
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        Lecture lecture = em.find(Lecture.class, id);
        return Optional.ofNullable(lecture);
    }

    @Override
    public void update(Lecture lecture) {

    }

    @Override
    public void delete(Lecture lecture) {

    }

    @Override
    public List<Lecture> findAll() {
        return em.createQuery("select l from Lecture l", Lecture.class).getResultList();
    }
}
