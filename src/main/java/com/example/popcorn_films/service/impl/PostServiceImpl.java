package com.example.popcorn_films.service.impl;

import com.example.popcorn_films.constants.ErrorMessages;
import com.example.popcorn_films.constants.Resources;
import com.example.popcorn_films.dto.PostDto;
import com.example.popcorn_films.entity.Like;
import com.example.popcorn_films.entity.Post;
import com.example.popcorn_films.entity.PostLike;
import com.example.popcorn_films.entity.User;
import com.example.popcorn_films.enums.UserRole;
import com.example.popcorn_films.exception.ResourceAlreadyExistsException;
import com.example.popcorn_films.exception.ResourceNotFoundException;
import com.example.popcorn_films.repository.PostLikeRepo;
import com.example.popcorn_films.repository.PostRepo;
import com.example.popcorn_films.repository.UserRepo;
import com.example.popcorn_films.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final ModelMapper mapper;
    private final UserRepo userRepo;
    private final PostLikeRepo postLikeRepo;


    @Override
    public PostDto savePost(PostDto postDto, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        if (postRepo.findByTitle(postDto.getTitle()).isPresent()) {
            throw new ResourceAlreadyExistsException(Resources.POST);
        }

        Post post = mapper.map(postDto, Post.class);
        post.setUser(user);

        return mapper.map(postRepo.save(post), PostDto.class);
    }

    @Override
    public List<PostDto> findAllPosts() {
        return postRepo.findAll().stream().map(post -> mapper.map(post, PostDto.class)).toList();
    }

    @Override
    public PostDto findPostById(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.POST, "id", String.valueOf(id)));
        return mapper.map(post, PostDto.class);
    }

    @Override
    @Transactional
    public PostDto updatePost(PostDto postDto, String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", email));

        Post post = postRepo.findById(postDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Resources.POST, "id", String.valueOf(postDto.getId())));

        if (user.getRole().equals(UserRole.EDITOR) && !post.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED);
        }

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        return mapper.map(post, PostDto.class);
    }

    @Override
    public void deletePostById(Long postId, String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", email));
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(Resources.POST, "id", String.valueOf(postId)));

        if (user.getRole().equals(UserRole.EDITOR) && !post.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED);
        }
        postRepo.delete(post);
    }

    @Override
    public void like(Long id, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", userEmail));
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Resources.POST, "id", String.valueOf(id)));

        PostLike postLike = PostLike.builder()
                .like(Like.builder()
                        .user(user)
                        .build())
                .post(post)
                .build();


        if (postLikeRepo.existsByLikeUserIdAndPostId(user.getId(), post.getId())) {
            throw new ResourceAlreadyExistsException(Resources.POST_LIKE);
        }

        postLikeRepo.save(postLike);
    }

    @Override
    public void unlike(Long id, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Resources.POST, "id", String.valueOf(id)));

        PostLike postLike = postLikeRepo.findByLikeUserIdAndPostId(user.getId(), post.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Resources.POST_LIKE));

        postLikeRepo.delete(postLike);
    }

    public Long getCountOfLikes(Long postId) {
        if (!postRepo.existsById(postId)) {
            throw new ResourceNotFoundException(Resources.POST, "id", String.valueOf(postId));
        }
        return postLikeRepo.countAllByPostId(postId);
    }
}
