package com.example.leetCodeRepetition.Controller;

import com.example.leetCodeRepetition.Model.Question;
import com.example.leetCodeRepetition.Model.User;
import com.example.leetCodeRepetition.Repo.QuestionRepository;
import com.example.leetCodeRepetition.Repo.UserRepository;
import com.example.leetCodeRepetition.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.leetCodeRepetition.utils.MyLogger;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/questions")
public class QuestionController {

    @Autowired
    private UserRepository userRepository;
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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    logger.info("Authentication: " + authentication.toString());
    String email = authentication.getName();

    // Use UserRepository to load user details
    User user = userRepository.findUserByEmail(email);

    // Assuming your User model has a method getId
    Integer owner_id = user.getId();
    question.setOwner_id(owner_id);
    logger.info("Creating question: " + question.toString());
    String url = question.getUrl();
    logger.info("URL: " + url);
    if(url.charAt(url.length()-1) == '/'){
        logger.info("URL ends with / " + url.substring(0,question.getUrl().length()-1));
        question.setUrl(url.substring(0,question.getUrl().length()-1));
    }
    if(question.getNext_review_long() != null){
        question.setNext_review(new java.sql.Timestamp(question.getNext_review_long()));
    }

    try {
        Question createdQuestion = repository.save(question);
        logger.info("Question created \n" + createdQuestion.toString());
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    } catch (DataIntegrityViolationException e) {
        // Handle the exception here. For example, you can log the error and return a meaningful message to the user.

        logger.error("error:"+e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
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
    public ResponseEntity<Object> findQuestionByOwnerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Use UserRepository to load user details
        User user = userRepository.findUserByEmail(email);

        // Assuming your User model has a method getId
        Integer owner_id = user.getId();

        List<Question> questions = repository.findQuestionByOwnerId(owner_id);
        if (questions.isEmpty()) {
            return new ResponseEntity<>("No question found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateQuestion(@RequestBody Question updatedQuestion) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    // Use UserRepository to load user details
    User user = userRepository.findUserByEmail(email);

    // Assuming your User model has a method getId
    Integer id = user.getId();
    Question existingQuestion = repository.findById(id).orElse(null);
    if (existingQuestion == null) {
        return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
    }
    if(updatedQuestion.getNext_review_long() != null){
        existingQuestion.setLast_completion(new Timestamp(System.currentTimeMillis()));
        existingQuestion.setNext_review(new java.sql.Timestamp(updatedQuestion.getNext_review_long()));
    }else{
        existingQuestion.setNext_review(null);
    }
    repository.save(existingQuestion);
    return new ResponseEntity<>(existingQuestion, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editQuestion(@PathVariable Integer id, @RequestBody Question updatedQuestion) {
        Question existingQuestion = repository.findById(id).orElse(null);
        if (existingQuestion == null) {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        existingQuestion.setTitle(updatedQuestion.getTitle());
        existingQuestion.setUrl(updatedQuestion.getUrl());
        existingQuestion.setTags(updatedQuestion.getTags());
        existingQuestion.setOwner_id(updatedQuestion.getOwner_id());
        existingQuestion.setTags(updatedQuestion.getTags());
        existingQuestion.setDifficulty(updatedQuestion.getDifficulty());
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
