package com.TeachingManager.TeachingManager.config.exceptions;

import com.TeachingManager.TeachingManager.domain.CustomUser;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.UUID;

public class WrongPasswordException extends BadCredentialsException {
    private final UUID pk;
    public WrongPasswordException(String msg, UUID userId) {
        super(msg);
        this.pk = userId;
    }

    // 유저의 비밀번호 틀린 횟수 증가시키는 함수
    public UUID getUserId() {
        return pk;
    }
}
