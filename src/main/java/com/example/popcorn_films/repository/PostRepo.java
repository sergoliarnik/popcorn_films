package com.example.popcorn_films.repository;

import com.example.popcorn_films.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {
}
