package com.example.leetCodeRepetition.Controller;

import com.example.leetCodeRepetition.Model.LeetCodeQuestion;
import com.example.leetCodeRepetition.Repo.LeetCodeQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/question")
public class LeetCodeQuestionController {
    @Autowired
    private LeetCodeQuestionRepository repository;

    public LeetCodeQuestionController(LeetCodeQuestionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public String hello(){
        return "Hello World";
    }
    @PostMapping("/add")
    public ResponseEntity<Object> createQuestion(@RequestBody LeetCodeQuestion question) {
        LeetCodeQuestion createdQuestion = repository.save(question);
        if (createdQuestion.getId() != null) {
            return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
        } else {
            // Rollback the insertion and return an error response
            repository.delete(createdQuestion);
            return new ResponseEntity<>("Failed to create question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll")
    public List<LeetCodeQuestion>getQuestion(){
        return repository.findAll();
    }


}
