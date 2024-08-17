package com.TeachingManager.TeachingManager.Repository.User;

import com.TeachingManager.TeachingManager.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(UUID userid);
    Optional<RefreshToken> findByRefreshToken(String RefreshToken);

}
