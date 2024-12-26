package com.quiz_service.feign;

import com.quiz_service.entity.QuestionWrapper;
import com.quiz_service.entity.Responses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Component
@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    //GENERATE THE QUIZ QUESTIONS
    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>>//this method returns a List of Integer of questions IDs
    getQuestionForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions);

    //GET QUESTIONS
    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    //GET SCORES
    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Responses> responses);
}
