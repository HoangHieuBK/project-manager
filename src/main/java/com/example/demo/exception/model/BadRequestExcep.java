package com.example.demo.exception.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestExcep extends RuntimeException {
    public BadRequestExcep(String exception){
        super(exception);
    }
}
