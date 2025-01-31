package com.example.springPhase3.dto;

import lombok.Data;

@Data
public class UserDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String type;
}
