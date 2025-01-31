package com.example.springPhase3.service;

import com.example.springPhase3.dto.QuestionResponseDTO;
import com.example.springPhase3.dto.QuestionStatistics;
import com.example.springPhase3.model.Question;
import com.example.springPhase3.model.QuestionOption;
import com.example.springPhase3.repository.QuestionRepository;
import com.example.springPhase3.repository.AnsweredQuestionsRepository;
import com.example.springPhase3.repository.QuestionOptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;


@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @Autowired
    private AnsweredQuestionsRepository answeredQuestionRepository;

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public Long createQuestion(String questionText, String[] options, Long correctOption, Long level, Long categoryId, Long designerId) {
        // Create and save question entity
        Question question = new Question();
        question.setQuestion(questionText);
        question.setCorrectOption(correctOption);
        question.setLevel(level);
        question.setCategoryId(categoryId);
        question.setDesignerId(designerId);
        questionRepository.save(question);

        // Save options
        for (String option : options) {
            QuestionOption questionOption = new QuestionOption();
            questionOption.setQuestionId(question.getId());
            questionOption.setOption(option);
            questionRepository.saveOption(questionOption.getQuestionId(),option); // Custom query or method to save options
        }

        return question.getId();
    }
    // TODO:
    // public List<QuestionStatistics> getQuestionStatisticsByDesignerId(Long designerId) {
    //     return questionRepository.getStatisticsByDesigner(designerId);
    // }

    public List<QuestionResponseDTO> getDesignedQuestions(Long designerId) {
        List<Question> questions = questionRepository.findByDesigner_Id(designerId);
        return questions.stream().map(question -> new QuestionResponseDTO(
                question.getId(),
                question.getQuestion(),
                questionRepository.getCorrectCount(question.getId()),
                questionRepository.getIncorrectCount(question.getId())
        )).collect(Collectors.toList());
    }

    public List<QuestionResponseDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(question -> new QuestionResponseDTO(question.getId(), question.getQuestion(), null, null))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getAnsweredQuestions(Long userId) {
        return answeredQuestionRepository.findByUserId(userId).stream()                
                .map(answer -> {
                    Map<String, Object> answerMap = new HashMap<>();
                    answerMap.put("id", answer.getId());
                    answerMap.put("question", answer.getQuestion().getQuestionText());
                    answerMap.put("correct", answer.isCorrect());
                    answerMap.put("designer", Map.of(
                        "id", answer.getQuestion().getDesigner().getId(),
                        "name", answer.getQuestion().getDesigner().getFullName()));
                    return answerMap;
                }).toList();
    }

    public Map<String, Object> getNotAnsweredQuestion(Long userId, String type, Long categoryId) {
    List<Question> questions;

    if ("random".equals(type)) {
        questions = questionRepository.findNotAnsweredByUserIdRandom(userId);
    } else if ("category".equals(type)) {
        questions = questionRepository.findNotAnsweredByUserIdAndCategory(userId, categoryId);
    } else {
        throw new IllegalArgumentException("Invalid question type");
    }

    if (questions.isEmpty()) {
        return null; // No unanswered questions found
    }

    Question question = questions.get(0); // Assuming one question is needed
    List<QuestionOption> options = questionOptionRepository.findByQuestionId(question.getId());

    Map<String, Object> response = new HashMap<>();
    response.put("id", question.getId());
    response.put("question", question.getQuestionText());
    response.put("options", options.stream().map(QuestionOption::getOptionText).toList());

    return response;
}

    
}
