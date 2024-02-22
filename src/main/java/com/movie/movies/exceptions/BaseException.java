package com.movie.movies.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseException extends RuntimeException {

    private final String errorKey;

    private final String message;

    public BaseException(String message, String errorKey) {
        super(message,null,false,false);
        this.errorKey = errorKey;
        this.message = message;
    }
}

