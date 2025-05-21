package com.example.e_ticketing.ticketing.controller.GlobalException;

import com.example.e_ticketing.ticketing.excpetion.*;
import com.example.e_ticketing.ticketing.excpetion.announcmentCustomException.PartialAnnouncementConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VisitPlaceDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleVisitPlaceDoesNotExistException(VisitPlaceDoesNotExistException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
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
    @ExceptionHandler(TicketTypeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleTicketTypeAlreadyExistsException(TicketTypeAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(PriceConfigAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePriceConfigAlreadyExistsException(PriceConfigAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClosureDateAlreadyAssignedException.class)
    public ResponseEntity<ErrorResponse> handleClosureDateAlreadyAssignedException(ClosureDateAlreadyAssignedException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PartialAnnouncementConflictException.class)
    public ResponseEntity<Map<String, Object>> handlePartialConflict(PartialAnnouncementConflictException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 207); // Or 409
        body.put("message", "Conflicts found on the following dates.");
        body.put("conflictedDates", ex.getConflictedDates());

        return ResponseEntity.status(207).body(body); // Multi-Status
    }


}
