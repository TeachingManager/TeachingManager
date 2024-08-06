package com.TeachingManager.TeachingManager.DTO.Token;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// 로그인 정보 전달
public class SetTokenRequest {
    private String email;
    private String password;
}
