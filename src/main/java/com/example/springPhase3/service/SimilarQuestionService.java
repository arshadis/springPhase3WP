package com.example.springPhase3.service;

import com.example.springPhase3.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springPhase3.model.*;
import java.util.List;


@Service
public class SimilarQuestionService {

    private final QuestionRepository questionRepository;
    private final SimilarQuestionRepository similarQuestionRepository;

    @Autowired
    public SimilarQuestionService(QuestionRepository questionRepository, SimilarQuestionRepository similarQuestionRepository) {
        this.questionRepository = questionRepository;
        this.similarQuestionRepository = similarQuestionRepository;
    }

    public boolean validateQuestionExists(Long questionId) {
        return questionRepository.existsById(questionId);
    }

    public List<Long> getSimilarQuestions(Long questionId) {
        return similarQuestionRepository.findSimilarQuestionIdsByQuestion_Id(questionId);
    }

    public void setSimilarQuestions(Long questionId, List<Long> similarQuestionIds) {
        for (Long similarQuestionId : similarQuestionIds) {
            if (!similarQuestionRepository.existsByQuestionIdAndSimilarQuestion_Id(questionId, similarQuestionId)) {
                SimilarQuestion similarQuestion = new SimilarQuestion();
                similarQuestion.setQuestion(questionRepository.findById(questionId).orElseThrow());
                similarQuestion.setSimilarQuestion(questionRepository.findById(similarQuestionId).orElseThrow());
                similarQuestionRepository.save(similarQuestion);
            }
        }
    }
}
