package com.question_service.controller;


import com.question_service.entity.Question;
import com.question_service.entity.QuestionWrapper;
import com.question_service.entity.Responses;
import com.question_service.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Question Controller", description = "Question Controller for adding and getting questions")
public class QuestionController {


    private final QuestionService questionService;
    Environment environment;

    public QuestionController(QuestionService questionService, Environment environment) {
        this.questionService = questionService;
        this.environment = environment;
    }


    //Add question
    @PostMapping("/add")
    @Operation(summary = "Generate a question", description = "Is to add a question")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("/allQuestions")
    @Operation(summary = "Get all questions", description = "Is to get All the questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    //GetQuestionsById
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Optional<Question> status = questionService.getQuestionById(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    //Get question by Category
    @GetMapping("/category/{category}")
    @Operation(summary = "Get all questions by category", description = "Is to get All the questions by category")
    public List<Question> getAllQuestionsByCategory(@PathVariable String category) {
        return questionService.getAllQuestionsByCategory(category);
    }

    @GetMapping("generate")
    //GENERATE THE QUIZ QUESTIONS
    public ResponseEntity<List<Integer>>
    getQuestionForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions) {
        return questionService.getQuestionForQuiz(categoryName, numQuestions);
    }

    //GET QUESTIONS
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
        System.out.println(environment.getProperty("local.server.port")+":");
        return questionService.getQuestionsFromId(questionIds);
    }

    //GET SCORES
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Responses> responses){
        return questionService.getScore(responses);
    }

}
