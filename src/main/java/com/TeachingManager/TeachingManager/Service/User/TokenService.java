package com.TeachingManager.TeachingManager.Service.User;

import com.TeachingManager.TeachingManager.DTO.Token.SetTokenResponse;
import com.TeachingManager.TeachingManager.Repository.User.RefreshTokenRepository;
import com.TeachingManager.TeachingManager.Service.User.Institute.InstituteDetailServiceImpl;
import com.TeachingManager.TeachingManager.Service.User.Teacher.TeacherDetailServiceImpl;
import com.TeachingManager.TeachingManager.config.jwt.TokenProvider;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;


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
    public SetTokenResponse LoginTokenCreate(String email, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        CustomUser user;

        try {
            user = (CustomUser) instituteService.loadUserByUsername(email);
            System.out.println("여기까지222");
        } catch (UsernameNotFoundException e) {
            // Institute 유저가 없을 경우 Teacher 유저 조회
            user = (CustomUser) teacherService.loadUserByUsername(email); // 여기서도 예외가 발생할 수 있으니 추가로 처리 필요
            System.out.println("Teacher 타입 임을 확인하여 반환함.");
        }
        // 어느 곳에도 유저가 존재하지 않는 경우
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new SetTokenResponse("Bearer", tokenProvider.createAccessToken(user,Duration.ofMinutes(30)), tokenProvider.createRefreshToken(user, Duration.ofHours(2)));
    }



    // 리프레쉬 토큰이 있는지 체크
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepo.findByRefreshToken(refreshToken).orElseThrow(()-> new IllegalArgumentException("Unexpected User"));
    }

    // 새 AccessToken 발급
    public String createNewAccessToken(String refreshToken) {
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userPk = findByRefreshToken(refreshToken).getUserId();
        Optional<CustomUser> instituteUser = Optional.ofNullable((CustomUser) instituteService.loadUserByPk(userPk));
        CustomUser user = instituteUser.orElseGet(() -> (CustomUser) teacherService.loadUserByPk(userPk));

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

    // 헤더를 받으면 Pk 값을 반환하는 함수
    public Long findPKInHeaderToken(String authorizationHeader) {
        return tokenProvider.getUserId(extractedToken(authorizationHeader));
    }

}
