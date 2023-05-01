package com.example.kunuz.controller;

import com.example.kunuz.exps.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class AdviceController {
    @ExceptionHandler({AppBadRequestException.class,
            ItemNotFoundException.class,
            ItemAlreadyExistsException.class,
            MethodNotAllowedException.class
    })
    public ResponseEntity<?> handlerException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
