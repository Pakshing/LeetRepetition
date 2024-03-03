//package com.example.leetCodeRepetition.Controller;
//
//import com.example.leetCodeRepetition.Model.User;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.Map;
//
//@Controller
//@RequestMapping("/oauth2/authorization/github")
//public class GitHubOAuthController {
//
//    @Value("${github.oauth2.client.id}")
//    private String clientId;
//
//    @Value("${github.oauth2.secret}")
//    private String clientSecret;
//
//    private final OAuth2AuthorizedClientService authorizedClientService;
//
//    public GitHubOAuthController(OAuth2AuthorizedClientService authorizedClientService) {
//        this.authorizedClientService = authorizedClientService;
//    }
//
//    @GetMapping("/callback")
//    public User getCallbackPage(OAuth2AuthenticationToken authentication, Model model) {
//        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("github", authentication.getName());
//        model.addAttribute("name", authentication.getName());
//        model.addAttribute("clientName", client.getClientRegistration().getClientName());
//
//        // Get the user's information
//        String userName = authentication.getName();
//        Map<String, Object> userAttributes = authentication.getPrincipal().getAttributes();
//
//        // Create a User object and set its properties
//        User user = new User();
//        user.setEmail(userName);
//
//
//        // Return the User object
//        return user;
//    }
//}