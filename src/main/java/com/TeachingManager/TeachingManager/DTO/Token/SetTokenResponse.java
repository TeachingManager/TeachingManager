package com.TeachingManager.TeachingManager.DTO.Token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// 처음 로그인시 클라이언트에게 전달해주는 Respons
public class SetTokenResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
