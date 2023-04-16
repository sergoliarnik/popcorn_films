package com.example.popcorn_films.mapper;

import com.example.popcorn_films.dto.CommentDto;
import com.example.popcorn_films.entity.Comment;
import com.example.popcorn_films.entity.FilmComment;
import com.example.popcorn_films.entity.PostComment;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class PostCommentMapper extends AbstractConverter<CommentDto, PostComment> {
    @Override
    protected PostComment convert(CommentDto source) {
        return PostComment.builder()
                .id(source.getId())
                .comment(Comment.builder()
                        .text(source.getText())
                        .build())
                .build();
    }
}