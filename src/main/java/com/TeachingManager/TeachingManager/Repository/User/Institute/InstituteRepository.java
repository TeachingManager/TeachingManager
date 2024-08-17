package com.TeachingManager.TeachingManager.Repository.User.Institute;

import com.TeachingManager.TeachingManager.domain.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstituteRepository extends JpaRepository<Institute, Long> {
    Optional<Institute> findByEmail(String email);
    Optional<Institute> findByPk(Long pk);
}
