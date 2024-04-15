package com.example.leetCodeRepetition.Controller;
import com.example.leetCodeRepetition.Model.User;
import com.example.leetCodeRepetition.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.leetCodeRepetition.utils.JwtUtil;
import com.example.leetCodeRepetition.utils.MyLogger;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/oauth2/github")
public class GithubOAuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil; //
    private final MyLogger logger = new MyLogger();
    @Value("${github.oauth2.client.id}")
    private String CLIENT_ID;
    @Value("${github.oauth2.secret}")
    private String CLIENT_SECRET;
    private static final String TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String USER_URL = "https://api.github.com/user";

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody String code,HttpServletResponse response) {
        logger.info("Received code: " + code);
        String accessToken = getAccessToken(code);
        logger.info("Access token: " + accessToken);
        String email = getUserEmail(accessToken);
        logger.info("User email: " + email);

        User user = userService.findUserByEmail(email);
        if (user == null) {
            user = new User(email, "github");
            user = userService.saveUser(user);
        }

        String token = jwtUtil.generateToken(user.getEmail());
        //cookie.setSecure(true);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Welcome, " + email + "!");
        responseBody.put("token", token);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
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

    @GetMapping("/google/oauth2/getUserEmail")
    public ResponseEntity<String> fetchGoogleUserEmailByAccessToken(@RequestParam("access_token") String accessToken) {
        String url = "https://www.googleapis.com/oauth2/v3/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            Map<String, Object> data = response.getBody();

            if (data != null && data.containsKey("email")) {
                logger.info("google " + data.get("email"));
                return new ResponseEntity<>((String) data.get("email"), HttpStatus.OK);
            }
        } catch (HttpClientErrorException e) {
            logger.error(String.valueOf(e));
            return new ResponseEntity<>("Failed to fetch email from Google", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Failure", HttpStatus.BAD_REQUEST);
    }
}