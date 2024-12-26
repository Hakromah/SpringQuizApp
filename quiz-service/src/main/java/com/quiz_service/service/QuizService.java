package com.quiz_service.service;

import com.quiz_service.entity.QuestionWrapper;
import com.quiz_service.entity.Quiz;
import com.quiz_service.entity.Responses;
import com.quiz_service.feign.QuizInterface;
import com.quiz_service.repository.QuizRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepo quizRepo;
    private final QuizInterface quizInterface;

    public QuizService(QuizRepo quizRepo, QuizInterface quizInterface) {
        this.quizRepo = quizRepo;
        this.quizInterface = quizInterface;
    }


    public ResponseEntity<String> createNewQuiz(String category, Integer nomQz, String title) {

        //(USAGE OF FeignClient):
        //Here we create a new question object from our question-service App (QuizInterface will get access to the
        // QuestionService App by using the service name from the Eureka-Service) as we annotated QuizInterface
        //with the service name from the Eureka-Service @FeignClient("QUESTION-SERVICE") FeignClient will
        // Use the service name from the Eureka-Service and make the Question App available for any implementation.
        List<Integer> questions = quizInterface.getQuestionForQuiz(category, nomQz).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsIds(questions);
        quizRepo.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizRepo.findById(id);
        if (quiz.isPresent()) {
            List<Integer> questionsIds = quiz.get().getQuestionsIds();

            return quizInterface.getQuestionsFromId(questionsIds);
        } else {
            throw new RuntimeException("No Question found from the database with the given id: " + id);
        }
    }

    // All the Score calculations is done in the QuestionService App
    public ResponseEntity<Integer> calculateResult(Integer id, List<Responses> responses) {
        return quizInterface.getScore(responses);
    }


//    public ResponseEntity<String> updateQuiz(Integer id, String category, Integer nomQz, String title) {
//        Optional<Quiz> quizOptional = quizRepo.findById(id);
//        if (quizOptional.isEmpty()) {
//            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
//        }
//
////        Quiz quiz = quizOptional.get();
////        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category, nomQz);
////        quiz.setTitle(title);
////        quiz.setQuestions(questions);
////
////        quizRepo.save(quiz);
//        return new ResponseEntity<>("Quiz updated successfully", HttpStatus.OK);
//    }
//
//    public ResponseEntity<String> deleteQuiz(Integer id) {
//        if (!quizRepo.existsById(id)) {
//            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
//        }
//
//        quizRepo.deleteById(id);
//        return new ResponseEntity<>("Quiz deleted successfully", HttpStatus.OK);
//    }
//
//
//    public ResponseEntity<List<Quiz>> findAllQuizs() {
//        List<Quiz> status = quizRepo.findAll();
//        return new ResponseEntity<>(status, HttpStatus.OK);
//    }
}