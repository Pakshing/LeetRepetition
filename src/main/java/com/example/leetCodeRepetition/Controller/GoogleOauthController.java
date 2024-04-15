package com.example.leetCodeRepetition.Controller;

import com.example.leetCodeRepetition.Model.User;
import com.example.leetCodeRepetition.Service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.leetCodeRepetition.utils.JwtUtil;
import com.example.leetCodeRepetition.utils.MyLogger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/oauth2/google")
public class GoogleOauthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;
    private final MyLogger logger = new MyLogger();
    @Value("${google.oauth2.client.id}")
    private String CLIENT_ID;
    @Value("${google.oauth2.secret}")
    private String CLIENT_SECRET;
    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String USER_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody String code) {
        logger.info("Received code: " + code);
        String accessToken = getAccessToken(code);
        logger.info("Access token: " + accessToken);
        String email = getUserEmail(accessToken);
        logger.info("User email: " + email);

        User user = userService.findUserByEmail(email);
        if (user == null) {
            user = new User(email, "google");
            user = userService.saveUser(user);
        }

        String token = jwtUtil.generateToken(user.getEmail());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "Welcome, " + email + "!");

        return new ResponseEntity<>(response, HttpStatus.OK);
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
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", "http://localhost:3000");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(TOKEN_URL, request, Map.class);
        logger.info("Access token response: " + response.getBody().get("access_token"));
        return (String) response.getBody().get("access_token");
    }

    private String getUserEmail(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<Map> response = restTemplate.exchange(USER_URL, HttpMethod.GET, entity, Map.class);

        return (String) response.getBody().get("email");
    }

    @GetMapping("/callback")
    public ResponseEntity<Object> handleGoogleCallback(@RequestParam("code") String code) {
    logger.info("Received callback with code: " + code);

    // Use the code to get the access token
    String accessToken = getAccessToken(code);
    logger.info("Access token: " + accessToken);

    // Use the access token to get the user's email
    String email = getUserEmail(accessToken);
    logger.info("User email: " + email);

    // Find or create the user and generate a JWT token
    User user = userService.findUserByEmail(email);
    if (user == null) {
        user = new User(email, "google");
        user = userService.saveUser(user);
    }

    String token = jwtUtil.generateToken(user.getEmail());

    Cookie cookie = new Cookie("token", token);
    cookie.setHttpOnly(true);
    cookie.setSecure(true); // set this to true if you are using https
    cookie.setPath("/"); // available to entire application

    Map<String, String> response = new HashMap<>();
    response.put("token", token);
    response.put("message", "Welcome, " + email + "!!");

    // Redirect the user to the frontend with the JWT token or send it as a response
     //return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:3000")).build();
    return new ResponseEntity<>(response, HttpStatus.OK);
}
}