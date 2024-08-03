package com.TeachingManager.TeachingManager.Repository.User;

import com.TeachingManager.TeachingManager.domain.CustomUser;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<CustomUser, Long> {
//    Optional<CustomUser> findByEmail(String email);
//    Optional<CustomUser> findByPk(Long pk);
}
