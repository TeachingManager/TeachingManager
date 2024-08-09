package com.TeachingManager.TeachingManager.Repository.User.Teacher;

import com.TeachingManager.TeachingManager.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long > {

    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findByPk(Long pk);
    List<Teacher> findByInstitute_Pk(Long pk);
}
