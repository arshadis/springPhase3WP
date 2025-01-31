package com.example.springPhase3.dto;

public class QuestionResponseDTO {
    private Long id;
    private String question;
    private Long correctCount;
    private Long notCorrectCount;

    public QuestionResponseDTO(Long id, String question, Long correctCount, Long notCorrectCount) {
        this.id = id;
        this.question = question;
        this.correctCount = correctCount;
        this.notCorrectCount = notCorrectCount;
    }

    // Getters and setters...
}
