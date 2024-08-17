package com.TeachingManager.TeachingManager.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class JweUtil{
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 16; // 16 bytes tag length
    private static final int GCM_IV_LENGTH = 12; // 12 bytes IV length

    // 암호화 메서드
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv); // Generate random IV
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // Combine IV and encrypted text for easy decryption
        byte[] encryptedIvAndText = new byte[GCM_IV_LENGTH + encryptedBytes.length];
        System.arraycopy(iv, 0, encryptedIvAndText, 0, GCM_IV_LENGTH);
        System.arraycopy(encryptedBytes, 0, encryptedIvAndText, GCM_IV_LENGTH, encryptedBytes.length);

        return Base64.getEncoder().encodeToString(encryptedIvAndText);
    }

    // 복호화 메서드
    public static String decrypt(String encryptedText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);

        // Extract IV and encrypted text
        byte[] iv = new byte[GCM_IV_LENGTH];
        byte[] encryptedBytes = new byte[decodedBytes.length - GCM_IV_LENGTH];

        System.arraycopy(decodedBytes, 0, iv, 0, GCM_IV_LENGTH);
        System.arraycopy(decodedBytes, GCM_IV_LENGTH, encryptedBytes, 0, encryptedBytes.length);

        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}

