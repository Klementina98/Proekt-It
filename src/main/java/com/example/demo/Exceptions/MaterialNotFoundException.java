package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MaterialNotFoundException extends RuntimeException{
    public MaterialNotFoundException() {
        super("Material with this id was not found");
    }
}
