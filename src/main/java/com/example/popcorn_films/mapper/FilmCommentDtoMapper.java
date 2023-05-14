package com.example.popcorn_films.mapper;

import com.example.popcorn_films.dto.CommentDto;
import com.example.popcorn_films.dto.CommentResponseDto;
import com.example.popcorn_films.entity.FilmComment;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class FilmCommentDtoMapper extends AbstractConverter<FilmComment, CommentResponseDto> {

    @Override
    protected CommentResponseDto convert(FilmComment source) {
        return CommentResponseDto.builder()
                .id(source.getId())
                .text(source.getComment().getText())
                .userId(source.getComment().getUser().getId())
                .build();
    }
}
