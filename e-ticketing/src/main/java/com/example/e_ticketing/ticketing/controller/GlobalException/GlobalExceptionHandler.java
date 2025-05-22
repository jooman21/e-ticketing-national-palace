package com.example.e_ticketing.ticketing.controller.GlobalException;

import com.example.e_ticketing.ticketing.excpetion.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



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

    @ExceptionHandler(AnnouncementNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAnnouncementNotFoundException(AnnouncementNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AvailabilityConflictException.class)
    public ResponseEntity<ErrorResponse> handleAvailabilityConflictException(AvailabilityConflictException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DuplicatePartialAvailabilityException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePartialAvailabilityException(DuplicatePartialAvailabilityException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(NoMuseumClosedDateFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoGateClosedDateFoundException(NoMuseumClosedDateFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidTimeSlotException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTimeSlotException(InvalidTimeSlotException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
