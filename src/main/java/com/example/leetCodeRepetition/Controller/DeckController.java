package com.example.leetCodeRepetition.Controller;

// create the same class as LeetCodeQuestionController.java

import com.example.leetCodeRepetition.Model.Deck;
import com.example.leetCodeRepetition.Model.Question;
import com.example.leetCodeRepetition.Repo.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.MyLogger;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/decks")
public class DeckController {
    @Autowired
    private final DeckRepository repository;
    private final MyLogger logger = new MyLogger();

    public DeckController(DeckRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createDeck(@RequestBody Deck deck) {
        logger.info("Creating deck: " + deck.toString());
        Deck createdDeck = repository.save(deck);
        logger.info("Creating deck: " + createdDeck.toString());
        if (deck.getId() != null) {
            return new ResponseEntity<>(createdDeck, HttpStatus.CREATED);
        } else {
            // Rollback the insertion and return an error response
            repository.delete(createdDeck);
            return new ResponseEntity<>("Failed to create deck", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findDeckById(@PathVariable Integer id) {
        Deck deck = repository.findById(id).orElse(null);
        if (deck == null) {
            return new ResponseEntity<>("Deck not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deck, HttpStatus.OK);
    }
    @GetMapping("/find")
    public ResponseEntity<Object> findDeckByUserId(@RequestParam Integer ownerId) {
        List<Deck> decks = repository.findDeckByDeckOwnerId(ownerId);
        if (decks.isEmpty()) {
            return new ResponseEntity<>("No deck found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(decks, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public List<Deck> findAllDeck() {
        return repository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteDeck(@PathVariable Integer id, @RequestParam Integer userId) {
        Deck deck = repository.findById(id).orElse(null);
        if (deck == null) {
            return new ResponseEntity<>("Deck not found", HttpStatus.NOT_FOUND);
        }
        if (deck.getDeckOwnerId() != userId) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        repository.delete(deck);
        return new ResponseEntity<>("Deck deleted", HttpStatus.OK);
    }

}

