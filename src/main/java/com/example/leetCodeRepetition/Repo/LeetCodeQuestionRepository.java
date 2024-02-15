package com.example.leetCodeRepetition.Repo;

import com.example.leetCodeRepetition.Model.LeetCodeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeetCodeQuestionRepository extends JpaRepository<LeetCodeQuestion, Integer> {

}
