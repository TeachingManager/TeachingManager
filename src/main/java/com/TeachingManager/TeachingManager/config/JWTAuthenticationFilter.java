package com.TeachingManager.TeachingManager.config;

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
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String token = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // "Bearer " 이후의 토큰만 추출
            token = authorizationHeader.substring(7);

            // 토큰을 사용하여 인증 처리
            System.out.println("Extracted Token: " + token);
        } else {
            // Authorization 헤더가 없거나 올바른 형식이 아닌 경우 처리
            System.out.println("Authorization header is missing or not valid");
        }

        if (tokenProvider.validToken(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Spring Security 에 있는 다양한 필터가 연쇄적으로 인증 검사를 하는데, 다음 필터 검사로 넘어가라는 뜻.
        // JWTAuthenticationFilter 는 OncePerRequestFilter 을 상속 받았기 때문에 Spring Security 의 인증 FilterChain  에서 한자리를 차지하고 있다.
        filterChain.doFilter(request, response);
    }
}
