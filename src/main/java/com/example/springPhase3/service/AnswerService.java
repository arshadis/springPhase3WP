package com.example.springPhase3.service;

import com.example.springPhase3.dto.AnswerStatistics;
import com.example.springPhase3.repository.AnsweredQuestionsRepository;
import com.example.springPhase3.repository.QuestionRepository;
import com.example.springPhase3.repository.UserRepository;
import com.example.springPhase3.model.Question;
import com.example.springPhase3.model.AnsweredQuestions;
import com.example.springPhase3.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnsweredQuestionsRepository answeredQuestionsRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    // This method will return the answer statistics for a player by their userId
    public AnswerStatistics getAnswerStatisticsByUserId(Long userId) {
        Object[] result = answeredQuestionsRepository.getAnswerStatisticsByUserId(userId);

        if (result != null && result.length == 2) {
            int correctAnswers = ((Number) result[0]).intValue();
            int notCorrectAnswers = ((Number) result[1]).intValue();
            return new AnswerStatistics(correctAnswers, notCorrectAnswers);
        }

        return new AnswerStatistics(0, 0);  // Return zero statistics if no data is found
    }


    public boolean existsByUserIdAndQuestion_Id(Long userId,Long questionId) {
        return answeredQuestionsRepository.existsByUserIdAndQuestion_Id(userId, questionId);  // Return zero statistics if no data is found
    }
    public boolean checkAnswer(Long userId, Long questionId, int option) {
        // Check if the question exists
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Check if the user already answered the question
        boolean alreadyAnswered = answeredQuestionsRepository.existsByUserIdAndQuestion_Id(userId, questionId);
        if (alreadyAnswered) {
            throw new RuntimeException("User has already answered this question");
        }

        // Determine correctness
        boolean isCorrect = question.getCorrectOption() == option;

        // Save the answer in the answered questions repository
        AnsweredQuestions answeredQuestions = new AnsweredQuestions();
        answeredQuestions.setUserId(userId);
        answeredQuestions.setQuestion(question);
        answeredQuestions.setCorrect(isCorrect);
        answeredQuestionsRepository.save(answeredQuestions);

        // Update the user's score
        int scoreChange = isCorrect ? 1 : -1;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setScore(user.getScore() + scoreChange);
        userRepository.save(user);

        return isCorrect;
    }
}
