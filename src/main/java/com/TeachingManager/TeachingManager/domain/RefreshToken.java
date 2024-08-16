package com.TeachingManager.TeachingManager.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "refresh_token", nullable = false, length = 1000)
    private String refreshToken;

    @Column(name = "expired_time", nullable = false)
    private Date expired_time;

    public RefreshToken(UUID userId, String refreshToken, Date expired_time){
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.expired_time = expired_time;
    }

    public RefreshToken update(String newRefreshToken){
        this.refreshToken = newRefreshToken;
        return this;
    }
}
