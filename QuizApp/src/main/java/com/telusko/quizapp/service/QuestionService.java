package com.telusko.quizapp.service;

import com.telusko.quizapp.entity.Question;
import com.telusko.quizapp.repository.QuestionRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepo repo;

    public QuestionService(QuestionRepo repo) {
        this.repo = repo;
    }


    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Error getting all questions" + e);
        }
    }

    public Optional<Question> getQuestionById(Integer id) {
        try {
            return repo.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // get all data from pgAdmin
    public List<Question> getAllQuestionsByCategory(String category) {
        try {
            return repo.findByCategory(category);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Add data to the database
    public ResponseEntity<String> addQuestion(Question question) {
        try {
            repo.save(question);
            return new ResponseEntity<>("Added Successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String removeQuestion(Integer id) {
        repo.deleteById(id);
        return "Question removed successfully";
    }

    public Question updateQuestion(Question question, Integer id) {

        return repo.findById(id).map(newQs -> {
            //newQs.setId(question.getId());
            newQs.setQuestionTitle(question.getQuestionTitle());
            newQs.setOption1(question.getOption1());
            newQs.setOption2(question.getOption2());
            newQs.setOption3(question.getOption3());
            newQs.setOption4(question.getOption4());
            newQs.setCategory(question.getCategory());
            newQs.setRightAnswer(question.getRightAnswer());
            return repo.save(newQs);

        }).orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

    }
}
