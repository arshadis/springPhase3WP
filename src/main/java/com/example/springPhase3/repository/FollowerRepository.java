package com.example.springPhase3.repository;

import com.example.springPhase3.model.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    Follower findByFollowerIdAndFollowedId(Long followerId, Long followedId);
    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);
}
