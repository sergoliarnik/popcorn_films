package com.example.popcorn_films.service;

import com.example.popcorn_films.dto.CommentDto;
import com.example.popcorn_films.dto.CommentResponseDto;

import java.util.List;

public interface FilmCommentService {
    List<CommentResponseDto> findFilmCommentsByFilmId(Long filmId);

    CommentResponseDto saveFilmComment(CommentDto commentDto, String userEmail, String filmApiId);

    CommentResponseDto updateFilmComment(CommentDto commentDto, String userEmail);

    void deleteFilmCommentById(Long filmCommentId, String userEmail);

    void like(Long filmCommentId, String userEmail);

    void unlike(Long filmCommentId, String userEmail);

    Long getCountOfLikes(Long filmCommentId);
}
