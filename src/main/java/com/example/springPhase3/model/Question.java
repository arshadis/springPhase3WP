package com.example.springPhase3.model;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(name = "correct_option", nullable = false)
    private Long correctOption;

    @Column(nullable = false)
    private Long level;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name = "designer_id", nullable = false)
    private User designer;

    public String getQuestionText() {
        return question;
    }

    public User getDesigner() {
        return designer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(Long correctOption) {
        this.correctOption = correctOption;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getDesignerId() {
        return designer.getId();
    }

    public void setDesignerId(Long designerId) {
        this.designer.setId(designerId);
    }
}
