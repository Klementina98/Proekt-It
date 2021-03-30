package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidUsernameException extends Exception{
    public InvalidUsernameException() {
        super("Invalid username");
    }
}
