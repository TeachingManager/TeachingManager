package com.TeachingManager.TeachingManager.service;

import com.TeachingManager.TeachingManager.domain.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureService {
    void registerLecture(Lecture lecture);

    Optional<Lecture> findLecture(long id);

    void updateLecture(Lecture lecture);

    void deleteLecture(Lecture lecture);

    List<Lecture> findAll();
}
