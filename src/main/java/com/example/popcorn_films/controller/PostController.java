package com.example.popcorn_films.controller;

import com.example.popcorn_films.dto.PostDto;
import com.example.popcorn_films.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Post Resource"
)
@RestController
@RequestMapping("api/posts")
public class PostController {
    private final PostService postService;


    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post",
            description = "Create post by using PostDto(id is ignored)"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> save(@RequestBody @Valid PostDto postDto){
        return new ResponseEntity<>(postService.savePost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> findAll(){
        return new ResponseEntity<>(postService.findAllPosts(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get Post",
            description = "Get post by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable Long id){
        return new ResponseEntity<>(postService.findPostById(id), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> update(@RequestBody @Valid PostDto postDto){
        return new ResponseEntity<>(postService.updatePost(postDto), HttpStatus.OK);
    }
}
