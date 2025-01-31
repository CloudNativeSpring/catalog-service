package com.jesse.catalogservice.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jesse.catalogservice.domain.BookAlreadyExistsException;
import com.jesse.catalogservice.domain.BookNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class BookControllerAdvice {
    
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String bookNotFoundExceptionHandler(BookNotFoundException e) {
        log.info("bookNotFoundExceptionHandler()");
        
        return e.getMessage();
    }
    
    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String bookAlreadyExistsExceptionHandler(BookAlreadyExistsException e) {
        log.info("bookAlreadyExistsExceptionHandler");
        
        return e.getMessage();
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.info("methodArgumentNotValidExceptionHandler()");
        
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult()
            .getAllErrors()
            .forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            });
        
        return errors;
    }

}
