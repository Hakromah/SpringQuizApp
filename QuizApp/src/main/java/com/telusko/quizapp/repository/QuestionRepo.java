package com.telusko.quizapp.repository;

import com.telusko.quizapp.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Integer> {

    @Query(value = "SELECT * FROM question WHERE LOWER(category) = LOWER(:category)", nativeQuery = true)
    List<Question> findByCategory(@Param("category") String category);

    @Query(value = "SELECT * FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQuestions", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(@Param("category") String category, @Param("numQuestions") Integer numQuestions);
}
