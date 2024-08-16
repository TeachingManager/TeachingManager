package com.TeachingManager.TeachingManager.config.exceptions;

public class AlreadyRegisteredException extends RuntimeException{
    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
