package com.rocky.controller;

import com.rocky.exception.CarNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Global exception handler for car-related exceptions.
 */
// This class handles exceptions thrown by the CarsController and provides appropriate HTTP responses.
@RestController
@ControllerAdvice
public class CarsExceptionHandlerController extends ResponseEntityExceptionHandler {


    /**
     * Handles CarNotFoundException.
     *
     * @param ex the CarNotFoundException thrown when a car is not found
     * @return a ResponseEntity with the HTTP status NOT_FOUND and the exception message
     */
    @ExceptionHandler(CarNotFoundException.class)
       public final ResponseEntity<Object> handleCarNotFoundException(CarNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
