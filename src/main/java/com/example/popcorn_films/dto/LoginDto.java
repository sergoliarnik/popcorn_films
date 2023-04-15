package com.example.popcorn_films.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.popcorn_films.constants.ValidationErrorMessages.USER_EMAIL_WRONG_FORMAT;
import static com.example.popcorn_films.constants.ValidationErrorMessages.USER_PASSWORD_LENGTH_RANGE_ERROR;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Email(message = USER_EMAIL_WRONG_FORMAT)
    private String email;

    @Size(min = 8, max = 12, message = USER_PASSWORD_LENGTH_RANGE_ERROR)
    private String password;
}
