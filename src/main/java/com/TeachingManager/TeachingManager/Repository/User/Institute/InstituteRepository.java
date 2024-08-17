package com.TeachingManager.TeachingManager.Repository.User.Institute;

import com.TeachingManager.TeachingManager.domain.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InstituteRepository extends JpaRepository<Institute, UUID> {
    Optional<Institute> findByEmail(String email);
    Optional<Institute> findByPk(UUID pk);
}
