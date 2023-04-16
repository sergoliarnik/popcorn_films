package com.example.popcorn_films.repository;

import com.example.popcorn_films.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepo extends JpaRepository<PostLike, Long> {
    Boolean existsByLikeUserIdAndPostId(Long userId, Long postId);
    Optional<PostLike> findByLikeUserIdAndPostId(Long userId, Long postId);
    Long countAllByPostId(Long id);
}
