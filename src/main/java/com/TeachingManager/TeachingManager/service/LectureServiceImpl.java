package com.TeachingManager.TeachingManager.service;

import com.TeachingManager.TeachingManager.domain.Lecture;
import com.TeachingManager.TeachingManager.Repository.Lecture.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LectureServiceImpl implements LectureService{

    private final LectureRepository lectureRepository;

    @Autowired
    public LectureServiceImpl(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Override
    public void registerLecture(Lecture lecture) {
        lectureRepository.save(lecture);
    }

    @Override
    public Optional<Lecture> findLecture(long id) {
        return lectureRepository.findById(id);
    }

    @Override
    public void updateLecture(Lecture lecture) {
        lectureRepository.update(lecture);
    }

    @Override
    public void deleteLecture(Lecture lecture) {
        lectureRepository.delete(lecture);
    }

    @Override
    public List<Lecture> findAll() {
        return lectureRepository.findAll();
    }
}
