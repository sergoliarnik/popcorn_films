package com.example.popcorn_films.repository;

import com.example.popcorn_films.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmRepo extends JpaRepository<Film,Long> {
    Optional<Film> findByApiTitleId(String apiTitleId);
    Boolean existsByApiTitleId(String apiTitleId);
}
