package com.TeachingManager.TeachingManager.config;

import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer";



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String url = request.getRequestURI();
        String method = request.getMethod();
        if ("/api/login".equals(url) || "/api/accessToken".equals(url) || ("/api/institute".equals(url) && "POST".equalsIgnoreCase(method) || ("/api/teacher".equals(url) && "POST".equalsIgnoreCase(method)))) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader  = request.getHeader("Authorization");
        String token;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token =  authorizationHeader.substring(7);
        } else {
            // Authorization 헤더가 없거나 올바른 형식이 아닌 경우 처리
            System.out.println("Authorization header is missing or not valid");
            token = null;
        }

        if (tokenProvider.validToken(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        else{
            System.out.println("유효하지 않은 토큰이었음, token = " + token);
        }

        // Spring Security 에 있는 다양한 필터가 연쇄적으로 인증 검사를 하는데, 다음 필터 검사로 넘어가라는 뜻.
        // JWTAuthenticationFilter 는 OncePerRequestFilter 을 상속 받았기 때문에 Spring Security 의 인증 FilterChain  에서 한자리를 차지하고 있다.
        filterChain.doFilter(request, response);
    }
}
