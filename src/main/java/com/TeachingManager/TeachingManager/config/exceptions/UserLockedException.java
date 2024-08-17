package com.TeachingManager.TeachingManager.config.exceptions;

public class UserLockedException extends RuntimeException{
    public UserLockedException(String message) {
        super(message);
    }
}
