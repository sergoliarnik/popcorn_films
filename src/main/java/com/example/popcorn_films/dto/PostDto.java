package com.example.popcorn_films.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        description = "PostDto Model Information"
)
public class PostDto {
    private Long id;

    @Schema(description = "Blog Post Title")
    @NotNull
    @Size(min = 2 ,max = 100,
            message = "The post title should have a minimum of 10 characters and a maximum of 100 characters.")
    private String title;

    @Schema(description = "Blog Post Content")
    @NotNull
    @Size(min = 2 ,max = 10000)
    private String content;
}
