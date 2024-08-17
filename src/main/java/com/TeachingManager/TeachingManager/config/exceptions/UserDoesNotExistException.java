package com.TeachingManager.TeachingManager.config.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDoesNotExistException extends UsernameNotFoundException {
    public UserDoesNotExistException(String msg) {
        super(msg);
    }
}
