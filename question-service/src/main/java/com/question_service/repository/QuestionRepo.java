package com.question_service.repository;


import com.question_service.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Integer> {

//    @Query(value = "SELECT * FROM question WHERE LOWER(category) = LOWER(:category)", nativeQuery = true)
//    List<Question> findByCategory(@Param("category") String category);

    List<Question> findByCategory(String category);

    @Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, Integer numQ);
}
