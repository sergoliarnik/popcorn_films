package com.example.popcorn_films.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceAlreadyExistsException extends RuntimeException{
    private final String resourceName;

    public ResourceAlreadyExistsException(String resourceName) {
        super(String.format("The %s already exists.", resourceName));
        this.resourceName = resourceName;
    }
}
