package com.TeachingManager.TeachingManager.config;

import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.config.jwt.JweInfo;
import com.TeachingManager.TeachingManager.config.jwt.JweUtil;
import com.TeachingManager.TeachingManager.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final JweInfo jweInfo;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer";



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 접근 경로 관련
        String url = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        Map<String, String> queryParams = parseQueryString(queryString);

        
        // 토큰 관련
        String authorizationHeader  = request.getHeader("Authorization");
        String token;


        //OAuth 2.0 관련 url 들
        if("/login/oauth2/code/naver".equals(url)
        || "/login/oauth2/code/google".equals(url)){
            filterChain.doFilter(request, response);
            return;
        }


        // 토큰 없이 접근 가능해야할 링크들
        if ("/api/accessToken".equals(url)
                || "/api/login".equals(url)
                || "/api/email/initial/prove".equals(url) // 초기 가입시 인증
                || "/api/email/locked/prove".equals(url) // 비번 틀렸을시 잠금해제
                || "/api/password/change".equals(url) // 비밀번호 찾을 이메일 송신
                || ("/api/institute".equals(url) && "POST".equalsIgnoreCase(method)
                || ("/api/teacher".equals(url) && "POST".equalsIgnoreCase(method)))
                || ("/email/initial/prove".equals(url) && "GET".equalsIgnoreCase(method))
                || ("/email/locked/prove".equals(url) && "GET".equalsIgnoreCase(method))
                || ("/password/change".equals(url) && "GET".equalsIgnoreCase(method))
                || ("/invite/teacher".equals(url) && "GET".equalsIgnoreCase(method))
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        // 로그인 하지 않아서 AccessToken 이 아닌 url 에 접근 권한 토큰이 있을 경우.
        if(("/email/initial/prove".equals(url) && queryParams.containsKey("token"))
                || ("/email/locked/prove".equals(url) && queryParams.containsKey("token")) // 이메일 본인인증
                || ("/password/change".equals(url) && queryParams.containsKey("token")) // 비밀번호 변경
                || ("/invite/teacher".equals(url) && queryParams.containsKey("token")) // 선생님 초대 api
        ){
            // 토큰 가져와서 복호화
            String inputToken = null;
            try {

                String decodedToken = URLDecoder.decode(queryParams.get("token"), StandardCharsets.UTF_8);
                inputToken = JweUtil.decrypt(decodedToken, jweInfo.getSecretKey());

                System.out.println("queryParams = " + queryParams.get("token"));
                System.out.println("decodedToken = " + decodedToken);
                System.out.println("inputToken = " + inputToken);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // 토큰 유효성 검사.
            if (tokenProvider.validToken(inputToken)){
                System.out.println("유효성 검사 통과함.");
                filterChain.doFilter(request, response);
            }
            else{
                throw new RuntimeException("유효하지 않은 토큰으로 접근 시도함.");
            }
            return;
        }
        
        /////////////// 다른 이외의 요청시
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

    private Map<String, String> parseQueryString(String queryString) {
        Map<String, String> queryParams = new HashMap<>();
        if (queryString != null) {
            String[] pairs = queryString.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                if (idx > 0) {
                    String key = pair.substring(0, idx);
                    String value = pair.substring(idx + 1);
                    queryParams.put(key, value);
                }
            }
        }
        return queryParams;
    }
}
