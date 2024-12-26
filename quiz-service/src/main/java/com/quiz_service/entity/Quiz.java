package com.quiz_service.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;

    @ElementCollection //This annotation is used instead of ManyToMany annotation if you are returning Numbers
    private List<Integer> questionsIds;

    public Quiz(Integer id, String title, List<Integer> questionsIds) {
        this.id = id;
        this.title = title;
        this.questionsIds = questionsIds;
    }

    public Quiz() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getQuestionsIds() {
        return questionsIds;
    }

    public void setQuestionsIds(List<Integer> questionsIds) {
        this.questionsIds = questionsIds;
    }
}
