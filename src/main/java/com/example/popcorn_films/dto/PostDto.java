package com.example.popcorn_films.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
    private Long id;
    @NotNull
    @Size(min = 2 ,max = 100)
    private String title;
    @NotNull
    @Size(min = 2 ,max = 10000)
    private String content;
}
