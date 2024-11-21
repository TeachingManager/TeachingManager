package com.TeachingManager.TeachingManager.EventHandler;


import com.TeachingManager.TeachingManager.DTO.Teacher.SocialTeacherInfo;
import com.TeachingManager.TeachingManager.DTO.Token.SetTokenResponse;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.Service.User.TokenForOAuth2Service;
import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.config.exceptions.AlreadyRegisteredException;
import com.TeachingManager.TeachingManager.config.exceptions.RegisteredInstituteEmailToOauthException;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Teacher;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TeacherRepository teacherRepo;
    private final InstituteRepository instRepo;
    private final TokenForOAuth2Service OAuth2TokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("OAuth2SuccessHandler 실행됨");

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 요청이 들어오 url
        String url = request.getRequestURI();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = null;
        String name = null;
        String provider = null;

        switch (url){
            case "/login/oauth2/code/naver":
                // 아래는 네이버 기준

                Map<String, Object> naverResponse = (Map<String, Object>) attributes.get("response");
                System.out.println("response = " + attributes.get("response"));

                email = (String) naverResponse.get("email");
                name = (String)  naverResponse.get("name");
                provider = "naver";
                break;

            case "/login/oauth2/code/google":
                // 아래는 구글 기준. 
                email = (String) attributes.get("email");
                name = (String) attributes.get("name");
                provider = "google";
                break;

            case "/login":
                // 아래는 구글 기준.
                email = (String) attributes.get("email");
                name = (String) attributes.get("name");
                provider = "google";
                break;

            default:
                break;
        }


        Optional<Institute> institute = instRepo.findByEmail(email);
        if(institute.isPresent()) {
            throw new RegisteredInstituteEmailToOauthException("Exists In Institute! OAuth2.0 login must be only teacher!");
        }
        
        Optional<Teacher> teacher = teacherRepo.findByEmail(email);

        ObjectMapper objectMapper = new ObjectMapper();
        
        
        // 기존에 가입하지 않았던 user 일 경우 가입처리
        if(teacher.isEmpty()) {
            Teacher newTeacher = new Teacher(email, name, provider);
            teacherRepo.save(newTeacher);
//            deleteCookie(response, "ACCOUNT_CHOOSER");
//            deleteCookie(response, "ACCOUNT_CHOOSER");
        }
//        deleteCookie(response, "쿠키명");

        // 로컬 JWT 토큰 생성하여 전달

        SetTokenResponse STR = OAuth2TokenService.OAuthLoginTokenCreate(email, name);

        String targetUrl = String.format("https://www.teachingmanager.online/oauth2/response?accessToken=%s&refreshToken=%s",
                URLEncoder.encode(STR.getAccessToken(), StandardCharsets.UTF_8),
                URLEncoder.encode(STR.getRefreshToken(), StandardCharsets.UTF_8));
        response.sendRedirect(targetUrl);
    }

    public void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null); // 쿠키 이름으로 새 쿠키를 생성
        cookie.setPath("/"); // 쿠키의 경로 설정
        cookie.setHttpOnly(true); // HttpOnly 속성 설정 (선택 사항)
        cookie.setMaxAge(0); // 쿠키의 만료 시간 설정 (0으로 설정하여 즉시 삭제)
        response.addCookie(cookie); // 응답에 쿠키 추가
    }
}
