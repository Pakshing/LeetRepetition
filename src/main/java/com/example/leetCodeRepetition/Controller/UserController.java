package com.example.leetCodeRepetition.Controller;

import com.example.leetCodeRepetition.Model.User;
import com.example.leetCodeRepetition.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.MyLogger;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    @Autowired
    private final UserRepository repository;
    private final MyLogger logger = new MyLogger();

    public UserController(UserRepository repository) {
        this.repository = repository;
    }


    // Add a new user
     @PostMapping("/add")
     public ResponseEntity<Object> createUser(@RequestBody User user) {
         logger.info("Creating user: " + user.getEmail());
         if(repository.findUserByEmail(user.getEmail()) != null){
                return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
         }
         User createdUser = repository.save(new User(user.getEmail()));
         if (createdUser.getId() != null) {
             return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
         } else {
             // Rollback the insertion and return an error response
             repository.delete(createdUser);
             return new ResponseEntity<>("Failed to create user", HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }

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
