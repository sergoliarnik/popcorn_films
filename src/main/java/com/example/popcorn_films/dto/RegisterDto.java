package com.example.popcorn_films.dto;

import com.example.popcorn_films.constants.ValidationErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @Size(min = 1, max = 30, message = ValidationErrorMessages.USER_NAME_LENGTH_RANGE_ERROR)
    private String name;
    @Size(min = 1, max = 30, message = ValidationErrorMessages.USER_SURNAME_LENGTH_RANGE_ERROR)
    private String surname;
    @Size(min = 1, max = 500, message = ValidationErrorMessages.USER_DESCRIPTION_LENGTH_RANGE_ERROR)
    private String description;
    @Email(message = ValidationErrorMessages.USER_EMAIL_WRONG_FORMAT)
    private String email;
    @Size(min = 8, max = 12, message = ValidationErrorMessages.USER_PASSWORD_LENGTH_RANGE_ERROR)
    private String password;
}
