package com.TeachingManager.TeachingManager.Repository.User.Teacher;

import com.TeachingManager.TeachingManager.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findByPk(UUID pk);
    List<Teacher> findByInstitute_Pk(UUID pk);
}
