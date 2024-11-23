package com.TeachingManager.TeachingManager.Service.Lecture;

import com.TeachingManager.TeachingManager.DTO.Lecture.AddLectureRequest;
import com.TeachingManager.TeachingManager.DTO.Lecture.LectureResponse;
import com.TeachingManager.TeachingManager.DTO.Lecture.UpdateLectureRequest;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureService {
    Lecture registerLecture(AddLectureRequest request, CustomUser user);

    LectureResponse findLecture(CustomUser user, Long id);

    LectureResponse updateLecture(UpdateLectureRequest request, CustomUser user, Long id);

    String deleteLecture(CustomUser user, Long id);

    List<LectureResponse> findAll(CustomUser user);
}
