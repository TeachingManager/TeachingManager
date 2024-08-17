package com.TeachingManager.TeachingManager.config.jwt;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Getter
@Setter
@Component
public class JweInfo {
    private SecretKey secretKey;

    @Value("${jwe.secret.key}")
    private String base64EncodedKey;

    @PostConstruct
    public void init() {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);
            secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize SecretKey from configuration", e);
        }
    }
}
