package com.TeachingManager.TeachingManager.config.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class WrongPasswordException extends BadCredentialsException {
    public WrongPasswordException(String msg) {
        super(msg);
    }
}
