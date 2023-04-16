package com.example.popcorn_films.repository;

import com.example.popcorn_films.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostCommentRepo extends JpaRepository<PostComment, Long> {
    @Query("FROM PostComment pc WHERE pc.post.id = :postId")
    List<PostComment> findAllByPostId(Long postId);
}
