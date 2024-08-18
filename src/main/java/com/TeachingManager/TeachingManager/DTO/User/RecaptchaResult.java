package com.TeachingManager.TeachingManager.DTO.User;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RecaptchaResult {
    private Boolean success;
    private Double score;
    private String action;
    private LocalDateTime challenge_ts;
    private String hostname;
}

