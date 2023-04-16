package com.example.popcorn_films.service;

import com.example.popcorn_films.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto savePost(PostDto postDto, String userEmail);
    List<PostDto> findAllPosts();
    PostDto findPostById(Long id);
    PostDto updatePost(PostDto postDto, String email);
    void deletePostById(Long id, String email);
    void like(Long id, String userEmail);

    void unlike(Long id, String userEmail);

    Long getCountOfLikes(Long postId);
}
