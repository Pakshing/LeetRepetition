package com.example.leetCodeRepetition.Controller;

import com.example.leetCodeRepetition.Model.Email;
import com.example.leetCodeRepetition.Model.User;
import com.example.leetCodeRepetition.Repo.UserRepository;
import com.example.leetCodeRepetition.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.leetCodeRepetition.utils.MyLogger;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    @Autowired
    private final UserRepository repository;
    @Autowired
    private JwtUtil jwtUtil; //
    private final MyLogger logger = new MyLogger();

    public UserController(UserRepository repository) {
        this.repository = repository;
    }




    // Add a new user
     @PostMapping("/add")
     public ResponseEntity<Object> createUser(@RequestBody User user) {
         logger.info("Creating user: " + user.getEmail());
         logger.info("Creating user: " + user.getLogin_method());
         if(repository.findUserByEmail(user.getEmail()) != null){
                logger.info("User already exists");
                return new ResponseEntity<>(repository.findUserByEmail(user.getEmail()), HttpStatus.OK);
         }
         User createdUser = repository.save(new User(user.getEmail(),user.getLogin_method()));
         if (createdUser.getId() != null) {
             return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
         } else {
             // Rollback the insertion and return an error response
             repository.delete(createdUser);
             return new ResponseEntity<>("Failed to create user", HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }

     @PostMapping("/testUserLogin")
     public ResponseEntity<Object> testUserLogin(@RequestBody Email emailObj) {
        logger.info("Logging in test user: " + emailObj.getEmail());
            String email = emailObj.getEmail();
         if(repository.findUserByEmail(email) == null){
             logger.info("No such test user:" +email);
             return new ResponseEntity<>("No such test user: "+email, HttpStatus.NOT_FOUND);
         }
         String token = jwtUtil.generateToken(email);
         //cookie.setSecure(true);
         Map<String, String> responseBody = new HashMap<>();
         responseBody.put("message", "Welcome, " + email + "!");
         responseBody.put("token", token);

         return new ResponseEntity<>(responseBody, HttpStatus.OK);

     }


//    @GetMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletResponse response) {
//        Cookie cookie = new Cookie("token", null); // name should be the same as the one you want to remove
//        cookie.setPath("/"); // path should be the same as the one you want to remove
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(0); // setting max age to 0 deletes the cookie
//        response.addCookie(cookie);
//        return new ResponseEntity<>("Logged out", HttpStatus.OK);
//    }

        // Find a user by email
    @GetMapping("/find")
    public ResponseEntity<Object> findUserByEmail(@RequestParam String email) {
        email = email.replace("\"", "");
        User user = repository.findUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // delete a user by email
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteUserByEmail(@RequestParam String email) {
        User user = repository.findUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        repository.delete(user);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }




}
