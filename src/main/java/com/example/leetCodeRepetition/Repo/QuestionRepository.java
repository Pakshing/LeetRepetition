package com.example.leetCodeRepetition.Repo;

import com.example.leetCodeRepetition.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query(value = "SELECT * FROM question WHERE owner_id = :owner_id order by last_completion DESC", nativeQuery = true)
    List<Question> findQuestionByOwnerId(Integer owner_id);
}
