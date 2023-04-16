package com.example.popcorn_films.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.example.popcorn_films.constants.ValidationErrorMessages.POST_CONTENT_LENGTH_RANGE_ERROR;
import static com.example.popcorn_films.constants.ValidationErrorMessages.POST_TITLE_LENGTH_RANGE_ERROR;

@Data
public class PostDto {
    private Long id;

    @NotNull
    @Size(min = 2, max = 100,
            message = POST_TITLE_LENGTH_RANGE_ERROR)
    private String title;

    @NotNull
    @Size(min = 2, max = 10000,
            message = POST_CONTENT_LENGTH_RANGE_ERROR)
    private String content;
}
