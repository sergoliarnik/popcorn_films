package com.example.popcorn_films.repository;

import com.example.popcorn_films.entity.SavedFilm;
import com.example.popcorn_films.enums.SavedFilmStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedFilmRepo extends JpaRepository<SavedFilm, Long> {
    Optional<SavedFilm> findByFilmIdAndStatus(Long filmId, SavedFilmStatus savedFilmStatus);

    List<SavedFilm> findByStatus(SavedFilmStatus savedFilmStatus);

    Boolean existsByFilmIdAndStatus(Long filmId, SavedFilmStatus savedFilmStatus);
}
