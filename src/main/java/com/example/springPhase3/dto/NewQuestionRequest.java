package com.example.springPhase3.dto;

public class NewQuestionRequest {

    private String question; // The question text
    private String firstOption; // The first option for the question
    private String secondOption; // The second option for the question
    private String thirdOption; // The third option for the question
    private String fourthOption; // The fourth option for the question
    private int correct; // The index of the correct option (1, 2, 3, or 4)
    private int level; // The difficulty level of the question
    private Long category; // The category ID to which the question belongs

    // Getters and setters

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFirstOption() {
        return firstOption;
    }

    public void setFirstOption(String firstOption) {
        this.firstOption = firstOption;
    }

    public String getSecondOption() {
        return secondOption;
    }

    public void setSecondOption(String secondOption) {
        this.secondOption = secondOption;
    }

    public String getThirdOption() {
        return thirdOption;
    }

    public void setThirdOption(String thirdOption) {
        this.thirdOption = thirdOption;
    }

    public String getFourthOption() {
        return fourthOption;
    }

    public void setFourthOption(String fourthOption) {
        this.fourthOption = fourthOption;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    // Method to validate fields in the request
    public boolean isValid() {
        if (question == null || question.trim().isEmpty()) {
            return false;
        }
        if (firstOption == null || firstOption.trim().isEmpty()) {
            return false;
        }
        if (secondOption == null || secondOption.trim().isEmpty()) {
            return false;
        }
        if (thirdOption == null || thirdOption.trim().isEmpty()) {
            return false;
        }
        if (fourthOption == null || fourthOption.trim().isEmpty()) {
            return false;
        }
        if (correct < 1 || correct > 4) {
            return false; // Correct answer should be between 1 and 4
        }
        if (level < 1 || level > 5) {
            return false; // Level should be between 1 and 5
        }
        if (category == null || category <= 0) {
            return false; // Category ID must be valid (non-zero positive number)
        }
        return true;
    }
}
