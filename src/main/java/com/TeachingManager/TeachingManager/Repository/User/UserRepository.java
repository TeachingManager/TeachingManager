package com.TeachingManager.TeachingManager.Repository.User;

import com.TeachingManager.TeachingManager.domain.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<CustomUser, Long> {
//    Optional<CustomUser> findByEmail(String email);
//    Optional<CustomUser> findByPk(Long pk);
}
