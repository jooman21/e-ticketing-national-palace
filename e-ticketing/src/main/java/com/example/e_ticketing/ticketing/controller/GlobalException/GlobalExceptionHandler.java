package com.example.e_ticketing.ticketing.controller.GlobalException;

import com.example.e_ticketing.ticketing.excpetion.PriceConfigDoesNotFoundException;
import com.example.e_ticketing.ticketing.excpetion.TicketTypeDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketTypeDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleTicketTypeDoesNotExistException(TicketTypeDoesNotExistException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PriceConfigDoesNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(PriceConfigDoesNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
