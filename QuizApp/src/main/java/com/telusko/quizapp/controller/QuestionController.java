package com.telusko.quizapp.controller;

import com.telusko.quizapp.entity.Question;
import com.telusko.quizapp.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Question Controller", description = "Question Controller for adding and getting questions")
public class QuestionController {


    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/allQuestions")
    @Operation(summary = "Get all questions", description = "Is to get All the questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Optional<Question> status = questionService.getQuestionById(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get all questions by category", description = "Is to get All the questions by category")
    public List<Question> getAllQuestionsByCategory(@PathVariable String category) {
        return questionService.getAllQuestionsByCategory(category);
    }

    @PostMapping("/add")
    @Operation(summary = "Add a question", description = "Is to add a question")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        String status = questionService.removeQuestion(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQuestion(@RequestBody Question question, @PathVariable Integer id) {
        try {
            Question status = questionService.updateQuestion(question, id);
            return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
