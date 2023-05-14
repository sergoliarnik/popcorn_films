package com.example.popcorn_films.dto;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.popcorn_films.constants.ValidationErrorMessages.FILM_COMMENT_TEXT_LENGTH_RANGE_ERROR;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    @Size(min = 1, max = 1000, message = FILM_COMMENT_TEXT_LENGTH_RANGE_ERROR)
    private String text;

    private Long userId;
}
