package com.example.popcorn_films.dto;

import com.example.popcorn_films.enums.UserRole;
import com.example.popcorn_films.enums.UserStatus;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String description;
    private UserRole role;
    private UserStatus status;
}
