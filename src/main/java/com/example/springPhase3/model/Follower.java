package com.example.springPhase3.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "followers")
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follower_id")
    private Long followerId;

    @Column(name = "followed_id")
    private Long followedId;
}
