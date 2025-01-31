package com.example.springPhase3.dto;

public class QuestionStatistics {
    private Long id;
    private Long correctCount;
    private Long notCorrectCount;

    public QuestionStatistics(Long id, Long correctCount, Long notCorrectCount) {
        this.id = id;
        this.correctCount = correctCount;
        this.notCorrectCount = notCorrectCount;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(Long correctCount) {
        this.correctCount = correctCount;
    }

    public Long getNotCorrectCount() {
        return notCorrectCount;
    }

    public void setNotCorrectCount(Long notCorrectCount) {
        this.notCorrectCount = notCorrectCount;
    }
}
