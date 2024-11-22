package com.TeachingManager.TeachingManager.Repository.Lecture;

import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LectureRepository {

    Lecture save(Lecture lecture);

    Optional<Lecture> findOneById(UUID instituteId, Long lectureId);

    void delete(UUID instituteId, Long lectureId);

    List<Lecture> findAll(UUID instituteId);

    List<Lecture> findAll_for_teacher(UUID instituteId, UUID teacherId);
}
