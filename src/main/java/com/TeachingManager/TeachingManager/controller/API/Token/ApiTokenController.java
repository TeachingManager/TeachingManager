package com.TeachingManager.TeachingManager.controller.API.Token;

import com.TeachingManager.TeachingManager.DTO.Token.AccessTokenRequest;
import com.TeachingManager.TeachingManager.DTO.Token.AccessTokenResponse;
import com.TeachingManager.TeachingManager.DTO.Token.SetTokenRequest;
import com.TeachingManager.TeachingManager.DTO.Token.SetTokenResponse;
import com.TeachingManager.TeachingManager.Service.User.RecaptchaService;
import com.TeachingManager.TeachingManager.Service.User.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RequiredArgsConstructor
@RestController
public class ApiTokenController {
    private final TokenService tokenService;
    private final RecaptchaService recaptchaService;

    @PostMapping("/api/login")
    public ResponseEntity<SetTokenResponse> login(
            @RequestBody SetTokenRequest request
    ) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        // Log the incoming request
        logger.debug("Received login request: {}", request);

        // 리캡챠 인증 성공시 로그인 단계
        if(recaptchaService.RecaptchaTest(request.getRecaptchaResponse(),"login")){
            String email = request.getEmail();
            String password = request.getPassword();
            return ResponseEntity.status(HttpStatus.CREATED).body(tokenService.LoginTokenCreate(email, password));
        }
        // 리캡챠 인증 실패시
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/api/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok()
                .body("로그아웃됨");
    }

    // RefreshToken 을 가지고, 새 AccessToken 을 발급받는 API
    @PostMapping("/api/accessToken")
    public ResponseEntity<AccessTokenResponse> createNewAccessToken(@RequestBody AccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new AccessTokenResponse(newAccessToken));

    }

}
