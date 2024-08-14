package com.TeachingManager.TeachingManager.Service.Lecture;

import com.TeachingManager.TeachingManager.DTO.Lecture.AddLectureRequest;
import com.TeachingManager.TeachingManager.DTO.Lecture.UpdateLectureRequest;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureService {
    Lecture registerLecture(AddLectureRequest request, CustomUser user);

    Lecture findLecture(CustomUser user, Long id);

    void updateLecture(UpdateLectureRequest request, CustomUser user, Long id);

    void deleteLecture(CustomUser user, Long id);

    List<Lecture> findAll(CustomUser user);
}
