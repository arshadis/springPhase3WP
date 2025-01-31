package com.example.springPhase3.service;

import com.example.springPhase3.model.Follower;
import com.example.springPhase3.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowerService {
    @Autowired
    private FollowerRepository followerRepository;

    public Follower findByFollowerAndFollowed(Long followerId, Long followedId) {
        return followerRepository.findByFollowerIdAndFollowedId(followerId, followedId);
    }

    public Follower save(Follower follower) {
        return followerRepository.save(follower);
    }

    public void delete(Follower follower) {
        followerRepository.delete(follower);
    }

    public boolean isFollowing(Long followerId, Long followedId) {
        return followerRepository.existsByFollowerIdAndFollowedId(followerId, followedId);
    }
}
