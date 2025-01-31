package com.example.springPhase3.model;

import jakarta.persistence.*;

@Entity
@Table(name = "similar_questions")
public class SimilarQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "similar_question_id", nullable = false)
    private Question similarQuestion;

    public void setSimilarQuestion(Question question) {
        this.similarQuestion = question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    // Getters and setters
}
