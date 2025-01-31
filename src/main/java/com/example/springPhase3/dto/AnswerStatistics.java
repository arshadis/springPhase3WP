package com.example.springPhase3.dto;

public class AnswerStatistics {
    private int correctAnswers;
    private int notCorrectAnswers;

    // Constructor
    public AnswerStatistics(int correctAnswers, int notCorrectAnswers) {
        this.correctAnswers = correctAnswers;
        this.notCorrectAnswers = notCorrectAnswers;
    }

    // Getters
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getNotCorrectAnswers() {
        return notCorrectAnswers;
    }

    // Setters (optional, depending on your use case)
    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setNotCorrectAnswers(int notCorrectAnswers) {
        this.notCorrectAnswers = notCorrectAnswers;
    }
}
