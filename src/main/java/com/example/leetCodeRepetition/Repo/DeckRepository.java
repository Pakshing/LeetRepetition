package com.example.leetCodeRepetition.Repo;

import com.example.leetCodeRepetition.Model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Integer> {
    @Query(value = "SELECT * FROM deck WHERE deck_owner_id = :id", nativeQuery = true)
    List<Deck> findDeckByDeckOwnerId(Integer id);
}
