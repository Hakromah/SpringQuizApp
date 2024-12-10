package com.telusko.quizapp.controller;

import com.telusko.quizapp.entity.QuestionWrapper;
import com.telusko.quizapp.entity.Quiz;
import com.telusko.quizapp.entity.Responses;
import com.telusko.quizapp.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@Tag(name = "QuizController")
@CrossOrigin(origins = "http://localhost:5173")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/createQz")
    @Operation(summary = "Create a new Quiz")
    ResponseEntity<String> creteQuiz(@RequestParam String category, @RequestParam int nomQz, @RequestParam String title) {
        return quizService.createNewQuiz(category, nomQz, title);
    }

    @GetMapping("/getQuizs")
    ResponseEntity<List<Quiz>> fetchAllQuiz() {
        return quizService.findAllQuizs();
    }

    @GetMapping("/getQuiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }

    // Responses for all questions returned for a particular quiz
    @PostMapping("/submitQz/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Responses> responses) {
        return quizService.calculateResult(id, responses);
    }

    @PutMapping("/updateQz/{id}")
    @Operation(summary = "Update an existing Quiz")
    public ResponseEntity<String> updateQuiz(
            @PathVariable Integer id,
            @RequestParam String category,
            @RequestParam int nomQz,
            @RequestParam String title) {
        return quizService.updateQuiz(id, category, nomQz, title);
    }

    @DeleteMapping("/deleteQz/{id}")
    @Operation(summary = "Delete an existing Quiz")
    public ResponseEntity<String> deleteQuiz(@PathVariable Integer id) {
        return quizService.deleteQuiz(id);
    }
}