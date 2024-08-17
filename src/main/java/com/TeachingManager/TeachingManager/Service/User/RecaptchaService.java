package com.TeachingManager.TeachingManager.Service.User;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@RequiredArgsConstructor
public class RecaptchaService {

    private final RestTemplate restTemplate;
    @Value("${recaptcha.secret_key}")
    private String secretKey;

    @Value("${recaptcha.site_key}")
    private String siteKey;

    @Value("${recaptcha.verify_url}")
    private String verify_url;

    @Value("${recaptcha.api_url}")
    private String api_url;

    @Value("${recaptcha.api_key}")
    private String api_key;


    public boolean RecaptchaTest(String recaptchaToken, String Action) {
        String url = UriComponentsBuilder.fromHttpUrl(verify_url)
                .queryParam("secret", secretKey)
                .queryParam("response", recaptchaToken)
                .toUriString();

        JsonNode response = restTemplate.postForObject(url, null, JsonNode.class);
        return response != null && response.path("score").asDouble() >= 0.9;
    }
}
