package com.example.popcorn_films.mapper;

import com.example.popcorn_films.dto.CommentDto;
import com.example.popcorn_films.entity.FilmComment;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class FilmCommentDtoMapper extends AbstractConverter<FilmComment, CommentDto> {

    @Override
    protected CommentDto convert(FilmComment source) {
        return CommentDto.builder()
                .id(source.getId())
                .text(source.getComment().getText())
                .build();
    }
}
