package com.boxfishedu.recommend.provider.app.exception;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public Object allException(Exception exception) {
        return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(exception.toString());
    }

}
