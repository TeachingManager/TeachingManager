package com.TeachingManager.TeachingManager.Repository.Student;

import com.TeachingManager.TeachingManager.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Student> findByInstitute_Pk(Long pk);
    Student save(Student student);

    Optional<Student> findById(Long Institute_id, Long student_id);

    String deleteById(Long institute_id, Long student_id);


}
