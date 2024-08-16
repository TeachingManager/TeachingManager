package com.TeachingManager.TeachingManager.config.exceptions;

public class UserDisabledException extends RuntimeException{
    public UserDisabledException(String message) {
        super(message);
    }
}
