package com.example.popcorn_films.mapper;

import com.example.popcorn_films.dto.CommentDto;
import com.example.popcorn_films.entity.Comment;
import com.example.popcorn_films.entity.FilmComment;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class FilmCommentMapper extends AbstractConverter<CommentDto, FilmComment> {
    @Override
    protected FilmComment convert(CommentDto source) {
        return FilmComment.builder()
                .id(source.getId())
                .comment(Comment.builder()
                        .text(source.getText())
                        .build())
                .build();
    }
}
