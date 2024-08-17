package com.TeachingManager.TeachingManager.Repository.Lecture;

//import com.TeachingManager.TeachingManager.domain.Lecture;
//
//import java.util.*;
//
//
//public class MemoryLectureRepository implements LectureRepository {
//
//    private static Map<Long, Lecture> store = new HashMap<>();
//    private static long sequence = 0L;
//
//    @Override
//    public void save(Lecture lecture) {
//        store.put(lecture.getLecture_id(), lecture);
//    }
//
//    @Override
//    public Optional<Lecture> findById(Long id) {
//        return Optional.ofNullable(store.get(id));
//    }
//
//    @Override
//    public void update(Lecture lecture) {
//        store.replace(lecture.getLecture_id(), lecture);
//    }
//
//    @Override
//    public void delete(Long id) {
//        store.remove(id.getLecture_id());
//    }
//
//    @Override
//    public List<Lecture> findAll() {
//        return new ArrayList<>(store.values());
//    }
//}
