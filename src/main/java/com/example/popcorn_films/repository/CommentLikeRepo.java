package com.example.popcorn_films.repository;

import com.example.popcorn_films.entity.CommentLike;
import com.example.popcorn_films.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepo extends JpaRepository<CommentLike, Long> {
    Boolean existsByLikeUserIdAndCommentId(Long userId, Long commentId);
    Optional<CommentLike> findByLikeUserIdAndCommentId(Long userId, Long commentId);

    Long countAllByCommentId(Long commentId);
}
