package com.example.popcorn_films.controller;


import com.example.popcorn_films.constants.HttpStatuses;
import com.example.popcorn_films.dto.CommentDto;
import com.example.popcorn_films.service.PostCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Post Comment Resource")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/post-comments")
@CrossOrigin
public class PostCommentController {
    private final PostCommentService postCommentService;

    @Operation(summary = "Find all post comments by post id")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @GetMapping
    ResponseEntity<List<CommentDto>> findAllByPostId(@RequestParam("post_id") Long postId) {
        return new ResponseEntity<>(postCommentService.findPostCommentsByPostId(postId), HttpStatus.OK);
    }

    @Operation(summary = "Save post comment")
    @ApiResponse(responseCode = "201", description = HttpStatuses.CREATED)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'USER')")
    @PostMapping
    ResponseEntity<CommentDto> save(@RequestBody CommentDto commentDto, @RequestParam("post_id") Long postId,
                                    Principal principal) {
        return new ResponseEntity<>(postCommentService.savePostComment(commentDto, principal.getName(), postId),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update post comment")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'USER')")
    @PutMapping
    ResponseEntity<CommentDto> update(@RequestBody CommentDto commentDto, Principal principal) {
        return new ResponseEntity<>(postCommentService.updatePostComment(commentDto, principal.getName()),
                HttpStatus.OK);
    }

    @Operation(summary = "Delete post comment by id")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'USER')")
    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> delete(@PathVariable Long id, Principal principal) {
        postCommentService.deletePostCommentById(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Like post comment")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'USER')")
    @PostMapping("/{id}/like")
    public ResponseEntity<HttpStatus> like(@PathVariable Long id, Principal principal) {
        postCommentService.like(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Unlike post comment")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'USER')")
    @DeleteMapping("/{id}/unlike")
    public ResponseEntity<HttpStatus> dislike(@PathVariable Long id, Principal principal) {
        postCommentService.unlike(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get count of likes")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @GetMapping("/{id}/likes/count")
    public ResponseEntity<Long> getCountOfLikes(@PathVariable Long id) {
        return new ResponseEntity<>(postCommentService.getCountOfLikes(id),HttpStatus.OK);
    }
}
