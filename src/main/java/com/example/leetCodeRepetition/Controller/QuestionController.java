package com.example.leetCodeRepetition.Controller;

import com.example.leetCodeRepetition.Model.Question;
import com.example.leetCodeRepetition.Repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.MyLogger;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/questions")
public class QuestionController {
    @Autowired
    private final QuestionRepository repository;
    private final MyLogger logger = new MyLogger();

    public QuestionController(QuestionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
    @PostMapping("")
    public ResponseEntity<Object> createQuestion(@RequestBody Question question) {
        logger.info("Creating question: " + question.toString());
        if(question.getNext_review_long() != null){
            question.setNext_review(new java.sql.Timestamp(question.getNext_review_long()));
        }

        Question createdQuestion = repository.save(question);
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
        Question question = repository.findById(id).orElse(null);
        if (question == null) {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(question, HttpStatus.OK);
    }



    @GetMapping("/findAll")
    public List<Question>findAllQuestion(){
        return repository.findAll();
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findQuestionByOwnerId(@RequestParam Integer owner_id) {
        logger.info("Finding question by owner_id: " + owner_id);
        List<Question> questions = repository.findQuestionByOwnerId(owner_id);
        if (questions.isEmpty()) {
            return new ResponseEntity<>("No question found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Integer id) {
        Question question = repository.findById(id).orElse(null);
        if (question == null) {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        repository.delete(question);
        return new ResponseEntity<>("Question deleted", HttpStatus.OK);
    }


}
