package com.TeachingManager.TeachingManager.controller;

import com.TeachingManager.TeachingManager.config.exceptions.UserDisabledException;
import com.TeachingManager.TeachingManager.config.exceptions.UserLockedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.AccountLockedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserLockedException.class)
    public ResponseEntity<String> handleAccountLockedException(UserLockedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserDisabledException.class)
    public ResponseEntity<String> handleAccountLockedException(UserDisabledException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

}
