package com.TeachingManager.TeachingManager.Service.User;

import com.TeachingManager.TeachingManager.DTO.Token.SetTokenResponse;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.Repository.User.RefreshTokenRepository;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.Service.User.Institute.InstituteDetailServiceImpl;
import com.TeachingManager.TeachingManager.Service.User.Institute.InstituteServiceImpl;
import com.TeachingManager.TeachingManager.Service.User.Teacher.TeacherDetailServiceImpl;
import com.TeachingManager.TeachingManager.Service.User.Teacher.TeacherServiceImpl;
import com.TeachingManager.TeachingManager.config.exceptions.UserDoesNotExistException;
import com.TeachingManager.TeachingManager.config.exceptions.WrongPasswordException;
import com.TeachingManager.TeachingManager.config.jwt.TokenProvider;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.RefreshToken;
import com.TeachingManager.TeachingManager.domain.Teacher;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepo;
    private final TokenProvider tokenProvider;
    private final TeacherDetailServiceImpl teacherService;
    private final InstituteDetailServiceImpl instituteService;
    private final AuthenticationManager authenticationManager;

    // 처음 로그인 시 access, refresh 토큰 발급하는 함수.
    // 반환값 :  클라이언트에 전달할 DTO
    @Transactional
    public SetTokenResponse LoginTokenCreate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        CustomUser user = null;
        System.out.println("logintokenCreate 의 authentication 직전.");
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            System.out.println("logintokenCreate 의 authentication 직후");

             user = (CustomUser) authentication.getPrincipal();
            if (user instanceof Teacher)
            {
                System.out.println("인증된 강사임");
                user.setFailedCount((byte) 0);
                return new SetTokenResponse("Bearer", tokenProvider.createAccessToken(user, Duration.ofMinutes(30)), tokenProvider.createRefreshToken(user, Duration.ofHours(2)));
            } else if (user instanceof Institute) {
                System.out.println("인증된 학원임");
                user.setFailedCount((byte) 0);
                return new SetTokenResponse("Bearer", tokenProvider.createAccessToken(user, Duration.ofMinutes(30)), tokenProvider.createRefreshToken(user, Duration.ofHours(2)));
            }
            System.out.println("사용자 타입이 정해지지 않았음?");
            return null;
        } catch (UserDoesNotExistException e) {
            System.out.println("없는 사용자!!");
            return null;
        } catch (WrongPasswordException bad) {
            throw bad;
        }
    }



    // 리프레쉬 토큰이 있는지 체크
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepo.findByRefreshToken(refreshToken).orElseThrow(()-> new IllegalArgumentException("Unexpected User"));
    }

    // 새 AccessToken 발급
    public String createNewAccessToken(String refreshToken) {

        if (!tokenProvider.validToken(refreshToken)) {
            System.out.println("유효하지 않은 리프레시 토큰!");
            throw new IllegalArgumentException("Unexpected token");
        }

        UUID userPk = findByRefreshToken(refreshToken).getUserId();
        Optional<CustomUser> instituteUser = Optional.ofNullable((CustomUser) instituteService.loadUserByPk(userPk));
        CustomUser user = instituteUser.orElseGet(() -> (CustomUser) teacherService.loadUserByPk(userPk));

        System.out.println("여기까지");
        String Token  = tokenProvider.createAccessToken(user, Duration.ofHours(2));
        System.out.println("Token = " + Token);
        return Token;
    }


    // 헤더에서 토큰을 추출하는 함수.
    public String extractedToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            // Authorization 헤더가 없거나 올바른 형식이 아닌 경우 처리
            System.out.println("Authorization header is missing or not valid");
            return null; // 유효하지 않은 경우 null 반환
        }
    }



}
