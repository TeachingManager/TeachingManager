package com.TeachingManager.TeachingManager.DTO.Token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AccessTokenResponse
{
//    private String grantType;
    private String accessToken;
    // 권한 까지 같이 보내기
}
