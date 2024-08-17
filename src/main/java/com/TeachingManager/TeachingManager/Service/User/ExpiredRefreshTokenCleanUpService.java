package com.TeachingManager.TeachingManager.Service.User;

import com.TeachingManager.TeachingManager.Repository.User.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExpiredRefreshTokenCleanUpService {
    private final RefreshTokenRepository refreshTokenRepo;

    @Scheduled(fixedRate = 3600000) // 1시간마다 실행
    public void deleteExpiredTokens() {
        Date now = new Date();
        // 만료된 토큰 삭제 메소드 추가하기.
    }
}
