package com.example.popcorn_films.dto;

import com.example.popcorn_films.enums.SavedFilmStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavedFilmDto {
    private Long id;
    private SavedFilmStatus status;
}
