package com.example.springPhase3.repository;

import com.example.springPhase3.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO options (question_id, option_text) VALUES (?1, ?2)", nativeQuery = true)
    public void saveOption(Long questionId, String optionText);

    // @Query("SELECT " +
    //        "SUM(CASE WHEN a.correct = true THEN 1 ELSE 0 END) AS correctAnswers, " +
    //        "SUM(CASE WHEN a.correct = false THEN 1 ELSE 0 END) AS notCorrectAnswers " +
    //        "FROM AnsweredQuestions a WHERE a.userId = :userId")
    // Object[] getAnswerStatisticsByUserId(Long userId);

    // @Query("""
    // SELECT new com.example.springphase3.dto.QuestionStatistics(
    //     q.id,
    //     COUNT(CASE WHEN aq.correct = true THEN 1 ELSE 0 END),
    //     COUNT(CASE WHEN aq.correct = false THEN 1 ELSE 0 END)
    // )
    // FROM Question q
    // LEFT JOIN AnsweredQuestions aq ON q.id = aq.question.id
    // WHERE q.designer.id = :designerId
    // GROUP BY q.id
    // """)
    // List<QuestionStatistics> getStatisticsByDesigner(@Param("designerId") Long designerId);
    // TODO:

    List<Question> findByDesigner_Id(Long designerId);


    @Query("SELECT COUNT(a) FROM AnsweredQuestions a WHERE a.question.id = :questionId AND a.correct = true")
    Long getCorrectCount(@Param("questionId") Long questionId);


    @Query("SELECT COUNT(a) FROM AnsweredQuestions a WHERE a.question.id = :questionId AND a.correct = false")
    Long getIncorrectCount(@Param("questionId") Long questionId);
    
    @Query(value = """
    SELECT * FROM questions q
    WHERE q.id NOT IN (
        SELECT aq.question_id FROM answered_questions aq WHERE aq.user_id = :userId
    )
    ORDER BY RAND()
    """, nativeQuery = true)
    List<Question> findNotAnsweredByUserIdRandom(@Param("userId") Long userId);

    @Query(value = """
    SELECT * FROM questions q
    WHERE q.category_id = :categoryId AND q.id NOT IN (
        SELECT aq.question_id FROM answered_questions aq WHERE aq.user_id = :userId
    )
    ORDER BY RAND()
    """, nativeQuery = true)
    List<Question> findNotAnsweredByUserIdAndCategory(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

}