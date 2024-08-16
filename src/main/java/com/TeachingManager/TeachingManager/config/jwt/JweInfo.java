package com.TeachingManager.TeachingManager.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Getter
@Setter
@Component
public class JweInfo {
    private SecretKey secretKey;
}
