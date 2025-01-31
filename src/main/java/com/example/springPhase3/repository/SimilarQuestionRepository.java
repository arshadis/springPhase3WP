package com.example.springPhase3.repository;

import com.example.springPhase3.model.SimilarQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface SimilarQuestionRepository extends JpaRepository<SimilarQuestion, Long> {
    @Query("SELECT sq.similarQuestion.id FROM SimilarQuestion sq WHERE sq.question.id = :questionId")
    List<Long> findSimilarQuestionIdsByQuestion_Id(@Param("questionId") Long questionId);

    boolean existsByQuestionIdAndSimilarQuestion_Id(Long questionId, Long similarQuestionId);
}
