package com.TeachingManager.TeachingManager.Service.User;

import com.TeachingManager.TeachingManager.DTO.Token.SetTokenResponse;
import com.TeachingManager.TeachingManager.Service.User.Institute.InstituteDetailServiceImpl;
import com.TeachingManager.TeachingManager.Service.User.Teacher.TeacherDetailServiceImpl;
import com.TeachingManager.TeachingManager.config.jwt.TokenProvider;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class TokenForOAuth2Service {
    private final TokenProvider tokenProvider;
    private final TeacherDetailServiceImpl teacherService;

    // OAuth2.0 을 이용하여 토큰 가져오기
    @Transactional
    public SetTokenResponse OAuthLoginTokenCreate(String email, String nickname){
        Teacher teacher = teacherService.loadTeacherByUsername(email);
        return new SetTokenResponse("Bearer", tokenProvider.createAccessToken(teacher, Duration.ofMinutes(30)), tokenProvider.createRefreshToken(teacher, Duration.ofHours(2)));
    }

}

