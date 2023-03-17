package com.example.popcorn_films.service;

import com.example.popcorn_films.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();

    PostDto getPostById(Long id);
}
