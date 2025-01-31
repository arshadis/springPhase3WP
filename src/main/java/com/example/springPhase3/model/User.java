package com.example.springPhase3.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String type;
    private String token;
    private Long score;

    public String getLastName() {
        return lastname;
    }
    public String getFirstName() {
        return firstname;
    }
    public String getFullName() {
        return firstname +" "+lastname;
    }

}
