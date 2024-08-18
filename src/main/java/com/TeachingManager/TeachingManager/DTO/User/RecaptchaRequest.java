package com.TeachingManager.TeachingManager.DTO.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecaptchaRequest {
    @JsonProperty("event")
    private Event event;

    // Getters and Setters

    @Getter
    @Setter
    public static class Event {
        @JsonProperty("token")
        private String token;

        @JsonProperty("expectedAction")
        private String expectedAction;

        @JsonProperty("siteKey")
        private String siteKey;

        // Getters and Setters
    }
}
