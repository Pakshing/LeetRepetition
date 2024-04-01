package com.example.leetCodeRepetition.Controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import utils.MyLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OAuthController {
    private final MyLogger logger = new MyLogger();
    @Value("${github.oauth2.client.id}")
    private String CLIENT_ID;
    @Value("${github.oauth2.secret}")
    private String CLIENT_SECRET;
    private static final String TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String USER_URL = "https://api.github.com/user";

    @GetMapping("github/oauth2/getUserEmail")
    public ResponseEntity<Object> getGithubCode(@RequestParam("code") String code) {
        logger.info("Received code: " + code);
        String accessToken = getAccessToken(code);
        logger.info("Access token: " + accessToken);
        String email = getUserEmail(accessToken);
        logger.info("User email: " + email);

        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    private String getAccessToken(String code) {
        logger.info("Getting access token");
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Map<String, String> params = new HashMap<>();
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        params.put("code", code);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(TOKEN_URL, request, Map.class);
        logger.info("Access token response: " + response.getBody().get("access_token"));
        return (String) response.getBody().get("access_token");
    }

    private String getUserEmail(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<List<Map>> response = restTemplate.exchange("https://api.github.com/user/emails", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map>>() {});

        return response.getBody().stream()
            .filter(emailMap -> Boolean.TRUE.equals(emailMap.get("primary")) && Boolean.TRUE.equals(emailMap.get("verified")))
            .map(emailMap -> (String) emailMap.get("email"))
            .findFirst()
            .orElse(null);
    }
}