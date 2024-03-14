package com.example.leetCodeRepetition.Controller;

import com.example.leetCodeRepetition.Model.Question;
import com.example.leetCodeRepetition.Repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.MyLogger;

import java.sql.Timestamp;
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
    String url = question.getUrl();
    if(url.charAt(url.length()-1) == '/'){
        question.setUrl(url.substring(0,question.getUrl().length()-1));
    }
    if(question.getNext_review_long() != null){
        question.setNext_review(new java.sql.Timestamp(question.getNext_review_long()));
    }

    try {
        Question createdQuestion = repository.save(question);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    } catch (DataIntegrityViolationException e) {
        // Handle the exception here. For example, you can log the error and return a meaningful message to the user.
        logger.error("A question with the same URL already exists.");
        return new ResponseEntity<>("A question with the same URL already exists.", HttpStatus.CONFLICT);
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

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateQuestion(@PathVariable Integer id, @RequestBody Question updatedQuestion) {
    Question existingQuestion = repository.findById(id).orElse(null);
    if (existingQuestion == null) {
        return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
    }
    existingQuestion.setTitle(updatedQuestion.getTitle());
    existingQuestion.setUrl(updatedQuestion.getUrl());
    existingQuestion.setCategory(updatedQuestion.getCategory());
    existingQuestion.setOwner_id(updatedQuestion.getOwner_id());
    existingQuestion.setTags(updatedQuestion.getTags());
    existingQuestion.setDifficulty(updatedQuestion.getDifficulty());
    existingQuestion.setLast_completion(new Timestamp(System.currentTimeMillis()));
    if(updatedQuestion.getNext_review_long() != null){
        existingQuestion.setNext_review(new java.sql.Timestamp(updatedQuestion.getNext_review_long()));
    }else{
        existingQuestion.setNext_review(null);
    }
    repository.save(existingQuestion);
    return new ResponseEntity<>(existingQuestion, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Integer id) {
        Question question = repository.findById(id).orElse(null);
        if (question == null) {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        repository.delete(question);
        return new ResponseEntity<>("Question deleted", HttpStatus.OK);
    }


}
