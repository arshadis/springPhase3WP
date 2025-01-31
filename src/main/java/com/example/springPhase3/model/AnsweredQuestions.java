package com.example.springPhase3.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "answered_questions")  // Make sure this matches your table name
public class AnsweredQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Assuming the primary key is an id

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "user_id")
    private Long userId;  // The user who answered the question

    @Column(name = "correct")
    private Boolean correct;  // Whether the answer was correct or not

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return question.getId();
    }


    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public Boolean isCorrect() {
        return correct;
    }
}
