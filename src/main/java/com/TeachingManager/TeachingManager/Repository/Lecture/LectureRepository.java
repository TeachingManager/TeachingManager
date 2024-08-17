package com.TeachingManager.TeachingManager.Repository.Lecture;

import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository {

    Lecture save(Lecture lecture);

    Optional<Lecture> findOneById(Long instituteId, Long lectureId);

    void delete(Long instituteId, Long lectureId);

    List<Lecture> findAll(Long instituteId);
}
