package com.example.popcorn_films.mapper;

import com.example.popcorn_films.dto.CommentDto;
import com.example.popcorn_films.entity.PostComment;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class PostCommentDtoMapper extends AbstractConverter<PostComment, CommentDto> {

    @Override
    protected CommentDto convert(PostComment source) {
        return CommentDto.builder()
                .id(source.getId())
                .text(source.getComment().getText())
                .build();
    }
}