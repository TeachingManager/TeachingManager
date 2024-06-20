package com.TeachingManager.TeachingManager.Repository.Lecture;

import com.TeachingManager.TeachingManager.domain.Lecture;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryLectureRepository implements LectureRepository {

    private static Map<Long, Lecture> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public void save(Lecture lecture) {
        lecture.setLectureId(++sequence);
        store.put(lecture.getLectureId(), lecture);
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void update(Lecture lecture) {
        store.replace(lecture.getLectureId(), lecture);
    }

    @Override
    public void delete(Lecture lecture) {
        store.remove(lecture.getLectureId());
    }

    @Override
    public List<Lecture> findAll() {
        return new ArrayList<>(store.values());
    }
}
