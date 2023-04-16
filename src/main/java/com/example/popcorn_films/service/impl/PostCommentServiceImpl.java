package com.example.popcorn_films.service.impl;

import com.example.popcorn_films.constants.ErrorMessages;
import com.example.popcorn_films.constants.Resources;
import com.example.popcorn_films.dto.CommentDto;
import com.example.popcorn_films.entity.CommentLike;
import com.example.popcorn_films.entity.Like;
import com.example.popcorn_films.entity.Post;
import com.example.popcorn_films.entity.PostComment;
import com.example.popcorn_films.entity.User;
import com.example.popcorn_films.enums.UserRole;
import com.example.popcorn_films.exception.ResourceAlreadyExistsException;
import com.example.popcorn_films.exception.ResourceNotFoundException;
import com.example.popcorn_films.repository.CommentLikeRepo;
import com.example.popcorn_films.repository.PostCommentRepo;
import com.example.popcorn_films.repository.PostRepo;
import com.example.popcorn_films.repository.UserRepo;
import com.example.popcorn_films.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {
    private final PostCommentRepo postCommentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final CommentLikeRepo commentLikeRepo;
    private final ModelMapper mapper;

    @Override
    public List<CommentDto> findPostCommentsByPostId(Long postId) {
        return postCommentRepo.findAllByPostId(postId).stream()
                .map(postComment -> mapper.map(postComment, CommentDto.class))
                .toList();
    }

    @Override
    public CommentDto savePostComment(CommentDto commentDto, String userEmail, Long postId) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(
                () -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(Resources.POST, "id", String.valueOf(postId)));

        PostComment postComment = mapper.map(commentDto, PostComment.class);

        postComment.setPost(post);
        postComment.getComment().setUser(user);

        return mapper.map(postCommentRepo.save(postComment), CommentDto.class);
    }

    @Override
    @Transactional
    public CommentDto updatePostComment(CommentDto commentDto, String userEmail) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(
                () -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        PostComment postComment = postCommentRepo.findById(commentDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Resources.POST_COMMENT, "id", String.valueOf(commentDto.getId())));

        if (!user.getId().equals(postComment.getComment().getUser().getId())) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED);
        }

        postComment.getComment().setText(commentDto.getText());

        return mapper.map(postComment, CommentDto.class);
    }

    @Override
    public void deletePostCommentById(Long postCommentId, String userEmail) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(
                () -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        PostComment postComment = postCommentRepo.findById(postCommentId).orElseThrow(
                () -> new ResourceNotFoundException(Resources.POST_COMMENT, "id", String.valueOf(postCommentId)));

        if (!user.getId().equals(postComment.getComment().getUser().getId())
                && !user.getRole().equals(UserRole.ADMIN)) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED);
        }

        postCommentRepo.delete(postComment);
    }

    @Override
    public void like(Long id, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", userEmail));
        PostComment postComment = postCommentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Resources.POST_COMMENT, "id", String.valueOf(id)));

        CommentLike commentLike = CommentLike.builder()
                .like(Like.builder()
                        .user(user)
                        .build())
                .comment(postComment.getComment())
                .build();


        if (commentLikeRepo.existsByLikeUserIdAndCommentId(user.getId(), postComment.getComment().getId())) {
            throw new ResourceAlreadyExistsException(Resources.COMMENT_LIKE);
        }

        commentLikeRepo.save(commentLike);
    }

    @Override
    public void unlike(Long id, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        PostComment postComment = postCommentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Resources.POST_COMMENT, "id", String.valueOf(id)));

        CommentLike commentLike = commentLikeRepo
                .findByLikeUserIdAndCommentId(user.getId(), postComment.getComment().getId())
                .orElseThrow(() -> new ResourceNotFoundException(Resources.COMMENT_LIKE));

        commentLikeRepo.delete(commentLike);
    }

    @Override
    public Long getCountOfLikes(Long postCommentId) {
        PostComment postComment = postCommentRepo.findById(postCommentId).orElseThrow(
                () -> new ResourceNotFoundException(Resources.POST_COMMENT, "id", String.valueOf(postCommentId)));

        return commentLikeRepo.countAllByCommentId(postComment.getComment().getId());
    }
}
