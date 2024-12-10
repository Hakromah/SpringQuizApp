package com.telusko.quizapp.service;

import com.telusko.quizapp.entity.Question;
import com.telusko.quizapp.entity.QuestionWrapper;
import com.telusko.quizapp.entity.Quiz;
import com.telusko.quizapp.entity.Responses;
import com.telusko.quizapp.repository.QuestionRepo;
import com.telusko.quizapp.repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepo quizRepo;

    public QuizService(QuizRepo quizRepo) {
        this.quizRepo = quizRepo;
    }

    @Autowired
    private QuestionRepo questionRepo;

    public ResponseEntity<String> createNewQuiz(String category, Integer nomQz, String title) {

        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category, nomQz);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepo.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizRepo.findById(id);
        if (quiz.isPresent()) {
            List<Question> questionsFromDB = quiz.get().getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();
            for (Question q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper(
                        q.getId(), q.getQuestionTitle(), q.getOption1(),
                        q.getOption2(), q.getOption3(), q.getOption4());
                questionsForUser.add(qw);
            }
            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        } else {
            throw new RuntimeException("No Question found from the database with the given id: " + id);
        }
    }

//    public ResponseEntity<Integer> calculateResult(Integer id, List<Responses> responses) {
//        int right = 0;
//        int i = 0;
//        Optional<Quiz> quiz = quizRepo.findById(id);
//        if (quiz.isPresent()) {
//            List<Question> questions = quiz.get().getQuestions();
//            for (Responses response : responses) {
//                if (response.getResponse().equals(questions.get(i).getRightAnswer())) {
//                    right++;
//
//                }
//                i++;
//                System.out.println("The Response is: " + response);
//            }
//
//        }
//        return new ResponseEntity<>(right, HttpStatus.OK);
//    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Responses> responses) {
        //UPDATED BY CGTP
        int right = 0;
        Optional<Quiz> quiz = quizRepo.findById(id);
        if (quiz.isPresent()) {
            List<Question> questions = quiz.get().getQuestions();
            for (Responses response : responses) {
                System.out.println("Question ID: " + response.getId()); // Debugging
                System.out.println("User Response: " + response.getResponse()); // Debugging
                for (Question question : questions) {
                    if (question.getId().equals(response.getId()) &&
                            question.getRightAnswer().equals(response.getResponse())) {
                        right++;
                    }
                }
            }
            System.out.println("Calculated Score: " + right); // Debugging
        } else {
            System.out.println("Quiz not found for ID: " + id); // Debugging
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }


    public ResponseEntity<String> updateQuiz(Integer id, String category, Integer nomQz, String title) {
        Optional<Quiz> quizOptional = quizRepo.findById(id);
        if (quizOptional.isEmpty()) {
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
        }

        Quiz quiz = quizOptional.get();
        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category, nomQz);
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizRepo.save(quiz);
        return new ResponseEntity<>("Quiz updated successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteQuiz(Integer id) {
        if (!quizRepo.existsById(id)) {
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
        }

        quizRepo.deleteById(id);
        return new ResponseEntity<>("Quiz deleted successfully", HttpStatus.OK);
    }


    public ResponseEntity<List<Quiz>> findAllQuizs() {
        List<Quiz> status = quizRepo.findAll();
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}