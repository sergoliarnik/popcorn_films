package com.example.popcorn_films.dto;

import com.example.popcorn_films.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
    private Long userId;
    private UserRole userRole;
    private String accessToken;
    private String tokenType = "Bearer";
}
