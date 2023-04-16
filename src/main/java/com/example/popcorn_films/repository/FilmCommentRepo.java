package com.example.popcorn_films.repository;

import com.example.popcorn_films.entity.FilmComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmCommentRepo extends JpaRepository<FilmComment, Long> {
    @Query("FROM FilmComment fc WHERE fc.film.id = :filmId")
    List<FilmComment> findAllByFilmId(Long filmId);
}
