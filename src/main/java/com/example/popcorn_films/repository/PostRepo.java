package com.example.popcorn_films.repository;

import com.example.popcorn_films.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepo extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);
}
