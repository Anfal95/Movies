package com.movie.movies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException{
    public NotFoundException(String message, String errorKey) {
        super(message, errorKey);
    }
}
