package com.example.popcorn_films.service;

import com.example.popcorn_films.dto.FilmDto;
import com.example.popcorn_films.enums.SavedFilmStatus;

import java.util.List;

public interface FilmService {
    FilmDto saveFilm(FilmDto filmDto);
    List<FilmDto> saveAllOnlyNewFilms(List<FilmDto> filmDtos);
    List<FilmDto> findAllFilms();
    FilmDto findFilmById(Long id);
    FilmDto updateFilm(FilmDto filmDto);
    void deleteFilmById(Long id);

    void addToSaved(Long id, SavedFilmStatus status, String userEmail);

    void removeFromSaved(Long id, SavedFilmStatus status, String userEmail);
}
