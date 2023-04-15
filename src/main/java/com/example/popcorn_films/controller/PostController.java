package com.example.popcorn_films.controller;

import com.example.popcorn_films.constants.HttpStatuses;
import com.example.popcorn_films.dto.PostDto;
import com.example.popcorn_films.service.PostService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Post Resource")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/posts")
public class PostController {
    private final PostService postService;

    @ApiResponse(responseCode = "201", description = HttpStatuses.CREATED)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PostMapping
    public ResponseEntity<PostDto> save(@RequestBody @Valid PostDto postDto, Principal principal){
        return new ResponseEntity<>(postService.savePost(postDto, principal.getName()), HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @GetMapping
    public ResponseEntity<List<PostDto>> findAll(){
        return new ResponseEntity<>(postService.findAllPosts(), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable Long id){
        return new ResponseEntity<>(postService.findPostById(id), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PutMapping
    public ResponseEntity<PostDto> update(@RequestBody @Valid PostDto postDto, Principal principal){
        return new ResponseEntity<>(postService.updatePost(postDto, principal.getName()), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id, Principal principal){
        postService.deletePostById(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/all")
    public ResponseEntity<HttpStatus> deleteAll(){
        postService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
