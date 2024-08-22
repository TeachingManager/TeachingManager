package com.TeachingManager.TeachingManager.EventHandler;


import com.TeachingManager.TeachingManager.DTO.Teacher.SocialTeacherInfo;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.Service.User.TokenForOAuth2Service;
import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.domain.Teacher;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TeacherRepository teacherRepo;
    private final TokenForOAuth2Service OAuth2TokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        System.out.println("OAuth2SuccessHandler 실행됨");

        // 아래는 구글 기준. Provider 을 구분하여 Naver, Google 에 맞게 형식이 다름.
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String provider = (String) attributes.get("provider");

        System.out.println("provider = " + provider);

        System.out.println("name = " + name);
        System.out.println("email = " + email);

        Optional<Teacher> teacher = teacherRepo.findByEmail(email);

        // 이미 있는 선생님일 경우 업데이트하지 않고 그대로 전달
        if (teacher.isPresent()) {
            response.sendRedirect("/home");
        }

        // 기존에 가입하지 않았던 user 일 경우
        else {
            Teacher newTeacher = new Teacher(email, name, "google");
            teacherRepo.save(newTeacher);
            response.sendRedirect("/additional_teacher_info");
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        System.out.println("OAuth2SuccessHandler 실행됨");


        // 아래는 구글 기준. Provider 을 구분하여 Naver, Google 에 맞게 형식이 다름.
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String provider = (String) attributes.get("provider");

        System.out.println("provider = " + provider);

        System.out.println("name = " + name);
        System.out.println("email = " + email);

        Optional<Teacher> teacher = teacherRepo.findByEmail(email);
        
        // 기존에 가입하지 않았던 user 일 경우 가입처리
        if(teacher.isEmpty()) {
            Teacher newTeacher = new Teacher(email, name, "google");
            teacherRepo.save(newTeacher);
//            response.sendRedirect("/additional_teacher_info");
        }

        // 로컬 JWT 토큰 생성하여 전달
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(OAuth2TokenService.OAuthLoginTokenCreate(email, name)));
        response.getWriter().flush();
    }
}
