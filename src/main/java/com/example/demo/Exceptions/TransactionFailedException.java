package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(String userId) {
        super(String.format("Transaction for user %s failed!",userId));
    }
}
