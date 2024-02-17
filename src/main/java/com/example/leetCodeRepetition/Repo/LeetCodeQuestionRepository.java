package com.example.leetCodeRepetition.Repo;

import com.example.leetCodeRepetition.Model.LeetCodeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeetCodeQuestionRepository extends JpaRepository<LeetCodeQuestion, Integer> {
    @Query(value = "SELECT * FROM leetCode_question WHERE deck_id = :deck_id", nativeQuery = true)
    List<LeetCodeQuestion> findQuestionByDeckId(Integer deck_id);

}
