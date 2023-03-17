package com.example.popcorn_films.service.impl;

import com.example.popcorn_films.dto.PostDto;
import com.example.popcorn_films.entity.Post;
import com.example.popcorn_films.exception.ResourceNotFoundException;
import com.example.popcorn_films.repository.PostRepo;
import com.example.popcorn_films.repository.UserRepo;
import com.example.popcorn_films.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;

    private final static String resourceName = "Post";

    @Autowired
    public PostServiceImpl(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        post.setUser(userRepo.findById(1L).orElseThrow());

        Post newPost = postRepo.save(post);

        return mapToDto(newPost);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepo.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", String.valueOf(id)));
        return mapToDto(post);
    }


    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return post;
    }
}
