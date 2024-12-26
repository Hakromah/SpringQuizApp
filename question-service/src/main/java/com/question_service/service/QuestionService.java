package com.question_service.service;


import com.question_service.entity.Question;
import com.question_service.entity.QuestionWrapper;
import com.question_service.entity.Responses;
import com.question_service.repository.QuestionRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepo repo;
    private final QuestionRepo questionRepo;

    public QuestionService(QuestionRepo repo, QuestionRepo questionRepo) {
        this.repo = repo;
        this.questionRepo = questionRepo;
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

    //GENERATE THE QUIZ QUESTION
    public ResponseEntity<List<Integer>> getQuestionForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions = questionRepo.findRandomQuestionsByCategory(categoryName, numQuestions);

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    //GET QUESTION ID FROM DB
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {

        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for (Integer id : questionIds) {
            questions.add(questionRepo.findById(id).get());
        }
        for (Question question : questions) {
            QuestionWrapper wrapper = new QuestionWrapper();
          wrapper.setId(question.getId());
          wrapper.setQuestionTitle(question.getQuestionTitle());
          wrapper.setOption1(question.getOption1());
          wrapper.setOption2(question.getOption2());
          wrapper.setOption3(question.getOption3());
          wrapper.setOption4(question.getOption4());
          wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    // GET THE SCORE AND RESPONSE
    public ResponseEntity<Integer> getScore(List<Responses> responses) {
        int right = 0;
        for (Responses response : responses) {
            Question question = questionRepo.findById(response.getId()).get();
            if (response.getResponse().equals(question.getRightAnswer())) {
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }


    ///   ////////////////////////////////////////////////////
//    public String removeQuestion(Integer id) {
//        repo.deleteById(id);
//        return "Question removed successfully";
//    }

//    public Question updateQuestion(Question question, Integer id) {
//
//        return repo.findById(id).map(newQs -> {
//            //newQs.setId(question.getId());
//            newQs.setQuestionTitle(question.getQuestionTitle());
//            newQs.setOption1(question.getOption1());
//            newQs.setOption2(question.getOption2());
//            newQs.setOption3(question.getOption3());
//            newQs.setOption4(question.getOption4());
//            newQs.setCategory(question.getCategory());
//            newQs.setRightAnswer(question.getRightAnswer());
//            return repo.save(newQs);
//
//        }).orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
//
//    }


}
