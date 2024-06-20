package com.TeachingManager.TeachingManager.Repository.Lecture;

import com.TeachingManager.TeachingManager.domain.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureRepository {
    void save(Lecture lecture);

    Optional<Lecture> findById(Long id);

    void update(Lecture lecture);

    void delete(Lecture lecture);

    List<Lecture> findAll();
}
