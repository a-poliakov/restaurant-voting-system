package ru.apolyakov.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableException  extends RuntimeException {
    private static final String  MESSAGE = "can't vote for the restaurant";

    public NotAcceptableException() {
        super(MESSAGE);
    }

    public NotAcceptableException(String message) {
        super(message);
    }
}
