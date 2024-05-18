package com.TeachingManager.TeachingManager.Repository.Teacher;

import com.TeachingManager.TeachingManager.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {


    Optional<Teacher> searchByEmail(String email);


}
