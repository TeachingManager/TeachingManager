package com.TeachingManager.TeachingManager.Service.User;

import com.TeachingManager.TeachingManager.Repository.User.RefreshTokenRepository;
import com.TeachingManager.TeachingManager.config.jwt.TokenProvider;
import com.TeachingManager.TeachingManager.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepo;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    // 리프레쉬 토큰이 있는지 체크
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepo.findByRefreshToken(refreshToken).orElseThrow(()-> new IllegalArgumentException("Unexpected User"));
    }


}
