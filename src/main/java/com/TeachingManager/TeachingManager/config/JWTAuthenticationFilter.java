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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer";



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /////////////////////////////////////////
        ////////////////////////////////////////

        // 헤더 출력
        System.out.println("//////////////////////////////////////");
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("Headers:");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);

            System.out.println(headerName + ": " + headerValue);
        }

        // 파라미터 출력
        Enumeration<String> parameterNames = request.getParameterNames();
        System.out.println("Parameters:");
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            System.out.println(paramName + ": " + paramValue);
        }

        // 요청 메서드 및 URI 출력
        System.out.println("Method: " + request.getMethod());
        System.out.println("Request URI: " + request.getRequestURI());

        // 요청 본문 출력 (선택 사항, 주의: 본문을 읽으면 이후 처리가 어려울 수 있음)
        if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;
            try {
                InputStream inputStream = request.getInputStream();
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int bytesRead = -1;
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                }
            } catch (IOException ex) {
                throw new ServletException("Error reading the request payload", ex);
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        throw new ServletException("Error closing bufferedReader", ex);
                    }
                }
            }
            String requestBody = stringBuilder.toString();
            System.out.println("Request Body: " + requestBody);}
            System.out.println("//////////////////////////////////////");



        /////////////////////////////////////////
        ////////////////////////////////////////


        String authorizationHeader = request.getHeader("Authorization");
        String token = null;

        System.out.println("request = " + request);
        System.out.println("authorizationHeader = " + authorizationHeader);
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // "Bearer " 이후의 토큰만 추출
            token = authorizationHeader.substring(7);

            // 토큰을 사용하여 인증 처리
            System.out.println("Extracted Token: " + token);
            System.out.println("11111111111111111");
        } else {
            // Authorization 헤더가 없거나 올바른 형식이 아닌 경우 처리
            System.out.println("Authorization header is missing or not valid");
        }

        if (tokenProvider.validToken(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("유효한 토크이었음, token = " + token);
        }

        // Spring Security 에 있는 다양한 필터가 연쇄적으로 인증 검사를 하는데, 다음 필터 검사로 넘어가라는 뜻.
        // JWTAuthenticationFilter 는 OncePerRequestFilter 을 상속 받았기 때문에 Spring Security 의 인증 FilterChain  에서 한자리를 차지하고 있다.
        filterChain.doFilter(request, response);
    }
}
