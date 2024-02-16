package com.example.leetCodeRepetition.Repo;

import com.example.leetCodeRepetition.Model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, Integer> {
}
