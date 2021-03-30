package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShoppingCartNotFoundException extends RuntimeException{

    public ShoppingCartNotFoundException(Long id) {
        super(String.format("Shopping cart with id: %d was not found", id));
    }

    public ShoppingCartNotFoundException(String userId) {
        super(String.format("Shopping cart for user: %s was not found", userId));
    }
}
