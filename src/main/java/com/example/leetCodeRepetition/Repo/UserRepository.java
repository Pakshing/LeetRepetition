package com.example.leetCodeRepetition.Repo;

import com.example.leetCodeRepetition.Model.LeetCodeQuestion;
import com.example.leetCodeRepetition.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>{
    @Query(value = "SELECT * FROM \"user\" WHERE email = :email", nativeQuery = true)
    User findUserByEmail(String email);
}
