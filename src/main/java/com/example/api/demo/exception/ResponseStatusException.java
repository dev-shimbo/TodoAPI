package com.example.api.demo.exception;

import com.example.api.demo.entity.response.ErrorResponseBody;

import lombok.Getter;

@Getter
public class ResponseStatusException extends RuntimeException{
    
    private ErrorResponseBody responseBody;

    public ResponseStatusException(String message, ErrorResponseBody responseBody){
        super(message);
        this.responseBody = responseBody;
    }
}
