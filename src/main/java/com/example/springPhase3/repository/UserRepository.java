package com.example.springPhase3.repository;

import com.example.springPhase3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    // Otional<User> findByEmail(Long );
    User findByIdAndType(Long id, String type);

    User findByToken(String token);

    List<User> findAllByTypeOrderByScoreDesc(String type);


    @Modifying
    @Query("UPDATE User u SET u.score = u.score + :scoreChange WHERE u.id = :userId")
    void updateScore(@Param("userId") Long userId, @Param("scoreChange") int scoreChange);
}
