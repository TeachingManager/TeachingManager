package com.TeachingManager.TeachingManager.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtInfo {
    private String jwtUser;
    private String sKey;
}
