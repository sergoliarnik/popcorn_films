package com.example.popcorn_films.repository;

import com.example.popcorn_films.entity.SavedFilm;
import com.example.popcorn_films.enums.SavedFilmStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedFilmRepo extends JpaRepository<SavedFilm, Long> {
    Optional<SavedFilm> findByUserIdAndFilmApiTitleIdAndStatus(Long userId, String filmApiId, SavedFilmStatus savedFilmStatus);

    List<SavedFilm> findByUserIdAndStatus(Long userId, SavedFilmStatus savedFilmStatus);

    Boolean existsByFilmIdAndStatus(Long filmId, SavedFilmStatus savedFilmStatus);
}
