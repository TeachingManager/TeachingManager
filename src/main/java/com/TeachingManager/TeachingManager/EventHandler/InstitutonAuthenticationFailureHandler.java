package com.TeachingManager.TeachingManager.EventHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.lang.model.SourceVersion;
import java.io.IOException;

@Component
public class InstitutonAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("여기까지는 들어왔음. 실패시 이벤트 핸들러");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 로그로 값 확인
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);


        // 여기서 추가적인 로직을 수행하거나 다른 리다이렉트 또는 처리를 구현할 수 있음
        // 예를 들어, 다른 URL로 리다이렉트하거나 특정 작업을 수행할 수 있음
        response.sendRedirect("/login/institute");
    }
}
