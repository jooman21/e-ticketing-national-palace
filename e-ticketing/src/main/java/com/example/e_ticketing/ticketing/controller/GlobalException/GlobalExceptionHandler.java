package com.example.e_ticketing.ticketing.controller.GlobalException;

import com.example.e_ticketing.ticketing.excpetion.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.e_ticketing.sys.common.core.domain.AjaxResult;
import com.example.e_ticketing.sys.common.core.text.Convert;
import com.example.e_ticketing.sys.common.exception.DemoModeException;
import com.example.e_ticketing.sys.common.exception.ServiceException;
import com.example.e_ticketing.sys.common.utils.StringUtils;
import com.example.e_ticketing.sys.common.utils.html.EscapeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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


    @ExceptionHandler(InvalidPriceConfigException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPriceConfigException(InvalidPriceConfigException ex) {
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

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDateException(InvalidDateException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ActiveTimeSlotNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleActiveTimeSlotNotFoundException(ActiveTimeSlotNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(TicketPolicyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTicketPolicyNotFoundException(TicketPolicyNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(TicketPolicyAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleTicketPolicyAlreadyExistsException(TicketPolicyAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidBookingException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBookingException(InvalidBookingException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }



    @ExceptionHandler(TimeSlotFullException.class)
    public ResponseEntity<ErrorResponse> handleTimeSlotFullException(TimeSlotFullException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTicketNotFoundException(TicketNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }








    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("Request URL '{}' encountered a permission validation failure '{}'", requestURI, e.getMessage());
        return AjaxResult.error(com.example.e_ticketing.sys.common.constant.HttpStatus.FORBIDDEN, "No permission. Please contact the administrator for authorization.");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("Request URL '{}' does not support '{}' method", requestURI, e.getMethod());
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request)
    {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public AjaxResult handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("A required path variable '{}' is missing from the request path '{}', a system exception occurred.", requestURI, e);
        return AjaxResult.error(String.format("A required path variable [%s] is missing from the request path", e.getVariableName()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public AjaxResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        String value = Convert.toStr(e.getValue());
        if (StringUtils.isNotEmpty(value))
        {
            value = EscapeUtil.clean(value);
        }
        log.error("Parameter type mismatch for '{}', a system exception occurred.", requestURI, e);
        return AjaxResult.error(String.format("Parameter type mismatch. Parameter [%s] requires type: '%s', but the provided value is: '%s'", e.getName(), e.getRequiredType().getName(), value));
    }

    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("Request URL '{}' encountered an unknown exception.", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("Request URL '{}' encountered a system exception.", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(message);
    }

    @ExceptionHandler(DemoModeException.class)
    public AjaxResult handleDemoModeException(DemoModeException e)
    {
        return AjaxResult.error("Demo mode, operation not allowed");
    }
}