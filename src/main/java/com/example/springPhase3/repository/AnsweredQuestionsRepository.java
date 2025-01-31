package com.example.springPhase3.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.springPhase3.model.AnsweredQuestions;
import java.util.List;

@Repository
public interface AnsweredQuestionsRepository extends CrudRepository<AnsweredQuestions, Long> {

    // Query to get the answer statistics (correct and incorrect answers) for a given userId
    @Query("SELECT " +
           "SUM(CASE WHEN a.correct = true THEN 1 ELSE 0 END) AS correctAnswers, " +
           "SUM(CASE WHEN a.correct = false THEN 1 ELSE 0 END) AS notCorrectAnswers " +
           "FROM AnsweredQuestions a WHERE a.userId = :userId")
    Object[] getAnswerStatisticsByUserId(Long userId);

    List<AnsweredQuestions> findByUserId(Long userId);
    boolean existsByUserIdAndQuestion_Id(Long userId, Long questionId);
}
