package com.quiz_service.controller;

import com.quiz_service.entity.QuestionWrapper;
import com.quiz_service.entity.QuizDto;
import com.quiz_service.entity.Responses;
import com.quiz_service.service.QuizService;
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

    @Operation(summary = "Create a new Quiz")
    @PostMapping("/createQz")
    ResponseEntity<String> creteQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createNewQuiz(
                quizDto.getCategoryName(),
                quizDto.getNumQuestions(),
                quizDto.getTitle()
        );
    }

    @GetMapping("/getQuiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }

//    @GetMapping("/getQuizs")
//    ResponseEntity<List<Quiz>> fetchAllQuiz() {
//        return quizService.findAllQuizs();
//    }

    // Responses for all questions returned for a particular quiz
    @PostMapping("/submitQz/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Responses> responses) {
        return quizService.calculateResult(id, responses);
    }

//    @PutMapping("/updateQz/{id}")
//    @Operation(summary = "Update an existing Quiz")
//    public ResponseEntity<String> updateQuiz(
//            @PathVariable Integer id,
//            @RequestParam String category,
//            @RequestParam int nomQz,
//            @RequestParam String title) {
//        return quizService.updateQuiz(id, category, nomQz, title);
//    }
//
//    @DeleteMapping("/deleteQz/{id}")
//    @Operation(summary = "Delete an existing Quiz")
//    public ResponseEntity<String> deleteQuiz(@PathVariable Integer id) {
//        return quizService.deleteQuiz(id);
//    }
}