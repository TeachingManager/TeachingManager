package com.TeachingManager.TeachingManager.Repository.User.Teacher;

import com.TeachingManager.TeachingManager.Repository.User.UserRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long > {

    Optional<Teacher> findByEmail(String email);
}
