package com.example.popcorn_films.service;

import com.example.popcorn_films.dto.CommentDto;

import java.util.List;

public interface PostCommentService {
    List<CommentDto> findPostCommentsByPostId(Long postId);

    CommentDto savePostComment(CommentDto commentDto, String userEmail, Long postId);

    CommentDto updatePostComment(CommentDto commentDto, String userEmail);

    void deletePostCommentById(Long postCommentId, String userEmail);

    void like(Long filmCommentId, String userEmail);

    void unlike(Long filmCommentId, String userEmail);

    Long getCountOfLikes(Long postCommentId);
}
