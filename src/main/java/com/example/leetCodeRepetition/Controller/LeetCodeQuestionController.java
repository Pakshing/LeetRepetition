package com.example.leetCodeRepetition.Controller;

import com.example.leetCodeRepetition.Model.LeetCodeQuestion;
import com.example.leetCodeRepetition.Repo.LeetCodeQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.MyLogger;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/questions")
public class LeetCodeQuestionController {
    @Autowired
    private final LeetCodeQuestionRepository repository;
    private final MyLogger logger = new MyLogger();

    public LeetCodeQuestionController(LeetCodeQuestionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/hello")
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

    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findQuestionById(@PathVariable Integer id) {
        LeetCodeQuestion question = repository.findById(id).orElse(null);
        if (question == null) {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(question, HttpStatus.OK);
    }



    @GetMapping("/findAll")
    public List<LeetCodeQuestion>findAllQuestion(){
        return repository.findAll();
    }

    @GetMapping("/findByDeckId/{deck_id}")
    public ResponseEntity<Object> findQuestionByDeckId(@PathVariable Integer deck_id) {
        List<LeetCodeQuestion> questions = repository.findQuestionByDeckId(deck_id);
        if (questions.isEmpty()) {
            return new ResponseEntity<>("No question found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/findByUserId/{user_id}")
    public ResponseEntity<Object> findQuestionByUserId(@PathVariable Integer user_id) {
        List<LeetCodeQuestion> questions = repository.findQuestionByDeckId(user_id);
        if (questions.isEmpty()) {
            return new ResponseEntity<>("No question found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Integer id) {
        LeetCodeQuestion question = repository.findById(id).orElse(null);
        if (question == null) {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        repository.delete(question);
        return new ResponseEntity<>("Question deleted", HttpStatus.OK);
    }


}
