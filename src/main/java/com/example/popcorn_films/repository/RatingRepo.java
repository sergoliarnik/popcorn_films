package com.example.popcorn_films.repository;

import com.example.popcorn_films.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepo extends JpaRepository<Rating, Long> {

    @Query("SELECT ROUND(AVG(mark), 1) FROM Rating where film.id = :filmId")
    Double findAvgMarkByFilmId(Long filmId);

    Boolean existsByFilmId(Long filmId);

    boolean existsByUserIdAndFilmId(Long filmId, Long userId);
}
