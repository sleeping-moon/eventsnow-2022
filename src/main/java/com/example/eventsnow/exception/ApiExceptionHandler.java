package com.example.eventsnow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


public class ApiExceptionHandler {


    @ExceptionHandler(value = {ApiReguestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiReguestException e){
        HttpStatus badRequest =HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(e.getMessage(), e, badRequest);
        return new ResponseEntity<>(apiException,badRequest);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleFileUploadError(MaxUploadSizeExceededException ex) {
        HttpStatus badRequest =HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(ex.getMessage(), ex, badRequest);
        return ResponseEntity.status(badRequest).body(apiException);
    }
}
