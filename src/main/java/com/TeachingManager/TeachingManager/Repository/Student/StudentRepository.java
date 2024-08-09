package com.TeachingManager.TeachingManager.Repository.Student;

import com.TeachingManager.TeachingManager.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByInstitute_Pk(Long pk);
}
