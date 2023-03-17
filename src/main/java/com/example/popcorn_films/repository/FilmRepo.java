package com.example.popcorn_films.repository;

import com.example.popcorn_films.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepo extends JpaRepository<Film,Long> {
}
