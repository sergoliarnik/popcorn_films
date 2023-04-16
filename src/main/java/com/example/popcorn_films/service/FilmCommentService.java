package com.example.popcorn_films.service;

import com.example.popcorn_films.dto.CommentDto;

import java.util.List;

public interface FilmCommentService {
    List<CommentDto> findFilmCommentsByFilmId(Long filmId);

    CommentDto saveFilmComment(CommentDto commentDto, String userEmail, Long filmId);

    CommentDto updateFilmComment(CommentDto commentDto, String userEmail);

    void deleteFilmCommentById(Long filmCommentId, String userEmail);

    void like(Long filmCommentId, String userEmail);

    void unlike(Long filmCommentId, String userEmail);
}
