package com.example.popcorn_films.service;

import com.example.popcorn_films.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto savePost(PostDto postDto, String userEmail);
    List<PostDto> findAllPosts();
    PostDto findPostById(Long id);
    PostDto updatePost(PostDto postDto);
    void deleteAll();
    void deletePostById(Long id, String email);
}
