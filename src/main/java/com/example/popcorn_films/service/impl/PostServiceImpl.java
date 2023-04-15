package com.example.popcorn_films.service.impl;

import com.example.popcorn_films.dto.PostDto;
import com.example.popcorn_films.entity.Post;
import com.example.popcorn_films.exception.ResourceNotFoundException;
import com.example.popcorn_films.repository.PostRepo;
import com.example.popcorn_films.repository.UserRepo;
import com.example.popcorn_films.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final ModelMapper mapper;
    private final UserRepo userRepo;

    private final static String resourceName = "Post";

    @Autowired
    public PostServiceImpl(PostRepo postRepo, ModelMapper mapper, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.mapper = mapper;
        this.userRepo = userRepo;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        post.setUser(userRepo.findById(1L).orElseThrow());

        Post newPost = postRepo.save(post);

        return mapper.map(newPost, PostDto.class);
    }

    @Override
    public List<PostDto> findAllPosts() {
        return postRepo.findAll().stream().map(post -> mapper.map(post, PostDto.class)).toList();
    }

    @Override
    public PostDto findPostById(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", String.valueOf(id)));
        return mapper.map(post, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto) {
        Long postId = postDto.getId();
        postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(resourceName, "id", String.valueOf(postId)));

        Post post = mapper.map(postDto, Post.class);

        return mapper.map(postRepo.save(post), PostDto.class);
    }
}
