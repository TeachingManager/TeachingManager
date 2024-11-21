package com.TeachingManager.TeachingManager.Repository.Student;

import com.TeachingManager.TeachingManager.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository {
    List<Student> findByInstitute_Pk(UUID pk);
    List<Student> findByInstitute_and_teacher_Pk(UUID instituteId, UUID teacherId, Short Year, Short Month);
    Student save(Student student);

    Optional<Student> findById(UUID Institute_id, Long student_id);

    String deleteById(UUID institute_id, Long student_id);

}
