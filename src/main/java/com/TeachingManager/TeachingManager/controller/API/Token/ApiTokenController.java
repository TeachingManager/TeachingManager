package com.TeachingManager.TeachingManager.controller.API.Token;

import com.TeachingManager.TeachingManager.DTO.Token.AccessTokenRequest;
import com.TeachingManager.TeachingManager.DTO.Token.AccessTokenResponse;
import com.TeachingManager.TeachingManager.DTO.Token.SetTokenRequest;
import com.TeachingManager.TeachingManager.DTO.Token.SetTokenResponse;
import com.TeachingManager.TeachingManager.Service.User.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ApiTokenController {
    private final TokenService tokenService;

    @PostMapping("/api/login")
    public ResponseEntity<SetTokenResponse> login(@RequestBody SetTokenRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        return ResponseEntity.status(HttpStatus.CREATED).body(tokenService.LoginTokenCreate(email, password));
    }

    // RefreshToken 을 가지고, 새 AccessToken 을 발급받는 API
    @PostMapping("/api/accessToken")
    public ResponseEntity<AccessTokenResponse> createNewAccessToken(@RequestBody AccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new AccessTokenResponse(newAccessToken));

    }
}
