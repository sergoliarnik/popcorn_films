package com.example.popcorn_films.service.impl;

import com.example.popcorn_films.constants.ErrorMessages;
import com.example.popcorn_films.constants.Resources;
import com.example.popcorn_films.dto.CommentDto;
import com.example.popcorn_films.dto.CommentResponseDto;
import com.example.popcorn_films.entity.CommentLike;
import com.example.popcorn_films.entity.Film;
import com.example.popcorn_films.entity.FilmComment;
import com.example.popcorn_films.entity.Like;
import com.example.popcorn_films.entity.PostComment;
import com.example.popcorn_films.entity.User;
import com.example.popcorn_films.enums.UserRole;
import com.example.popcorn_films.exception.ResourceAlreadyExistsException;
import com.example.popcorn_films.exception.ResourceNotFoundException;
import com.example.popcorn_films.repository.CommentLikeRepo;
import com.example.popcorn_films.repository.FilmCommentRepo;
import com.example.popcorn_films.repository.FilmRepo;
import com.example.popcorn_films.repository.UserRepo;
import com.example.popcorn_films.service.FilmCommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmCommentServiceImpl implements FilmCommentService {
    private final FilmCommentRepo filmCommentRepo;
    private final FilmRepo filmRepo;
    private final UserRepo userRepo;
    private final CommentLikeRepo commentLikeRepo;
    private final ModelMapper mapper;

    @Override
    public List<CommentResponseDto> findFilmCommentsByFilmId(Long filmId) {
        return filmCommentRepo.findAllByFilmId(filmId).stream()
                .map(filmComment -> mapper.map(filmComment, CommentResponseDto.class))
                .toList();
    }

    @Override
    @Transactional
    public CommentResponseDto saveFilmComment(CommentDto commentDto, String userEmail, String filmApiId) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(
                () -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        Film film = filmRepo.findByApiTitleId(filmApiId).orElseGet(() -> filmRepo.save(Film.builder().apiTitleId(filmApiId).build()));


        FilmComment filmComment = mapper.map(commentDto, FilmComment.class);

        filmComment.setFilm(film);
        filmComment.getComment().setUser(user);

        return mapper.map(filmCommentRepo.save(filmComment), CommentResponseDto.class);
    }

    @Override
    public CommentResponseDto updateFilmComment(CommentDto commentDto, String userEmail) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(
                () -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        FilmComment filmComment = filmCommentRepo.findById(commentDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Resources.FILM_COMMENT, "id", String.valueOf(commentDto.getId())));

        if (!user.getId().equals(filmComment.getComment().getUser().getId())) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED);
        }

        filmComment.getComment().setText(commentDto.getText());

        return mapper.map(filmComment, CommentResponseDto.class);
    }

    @Override
    public void deleteFilmCommentById(Long filmCommentId, String userEmail) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(
                () -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        FilmComment filmComment = filmCommentRepo.findById(filmCommentId).orElseThrow(
                () -> new ResourceNotFoundException(Resources.FILM_COMMENT, "id", String.valueOf(filmCommentId)));

        if (!user.getId().equals(filmComment.getComment().getUser().getId())
                && !user.getRole().equals(UserRole.ADMIN)) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED);
        }

        filmCommentRepo.delete(filmComment);
    }

    @Override
    public void like(Long id, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", userEmail));
        FilmComment filmComment = filmCommentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Resources.FILM_COMMENT, "id", String.valueOf(id)));

        CommentLike commentLike = CommentLike.builder()
                .like(Like.builder()
                        .user(user)
                        .build())
                .comment(filmComment.getComment())
                .build();


        if (commentLikeRepo.existsByLikeUserIdAndCommentId(user.getId(), filmComment.getComment().getId())) {
            throw new ResourceAlreadyExistsException(Resources.COMMENT_LIKE);
        }

        commentLikeRepo.save(commentLike);
    }

    @Override
    public void unlike(Long id, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        FilmComment filmComment = filmCommentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Resources.FILM_COMMENT, "id", String.valueOf(id)));

        CommentLike commentLike = commentLikeRepo
                .findByLikeUserIdAndCommentId(user.getId(), filmComment.getComment().getId())
                .orElseThrow(() -> new ResourceNotFoundException(Resources.COMMENT_LIKE));

        commentLikeRepo.delete(commentLike);
    }

    @Override
    public Long getCountOfLikes(Long filmCommentId) {
        FilmComment filmComment = filmCommentRepo.findById(filmCommentId).orElseThrow(
                () -> new ResourceNotFoundException(Resources.FILM_COMMENT, "id", String.valueOf(filmCommentId)));

        return commentLikeRepo.countAllByCommentId(filmComment.getComment().getId());
    }
}
