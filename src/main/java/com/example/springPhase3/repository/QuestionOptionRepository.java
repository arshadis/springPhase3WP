package com.example.springPhase3.repository;

import com.example.springPhase3.model.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
    // Built-in methods like save(), findById(), etc., are available
    List<QuestionOption> findByQuestionId(Long questionId);

}
