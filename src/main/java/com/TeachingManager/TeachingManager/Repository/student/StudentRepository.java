package com.TeachingManager.TeachingManager.Repository.student;

import com.TeachingManager.TeachingManager.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
