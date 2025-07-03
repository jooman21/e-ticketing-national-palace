package com.example.e_ticketing.ticketing.controller;
import com.example.e_ticketing.ticketing.application.dto.GroupBookingDto;
import com.example.e_ticketing.ticketing.application.response.TicketGroupBookingResponse;
import com.example.e_ticketing.ticketing.application.service.GroupBookingService;
import com.example.e_ticketing.ticketing.controller.GlobalException.ErrorResponse;
import com.example.e_ticketing.ticketing.excpetion.InvalidBookingException;
import com.example.e_ticketing.ticketing.excpetion.InvalidTicketTypeException;
import com.example.e_ticketing.ticketing.excpetion.InvalidTimeSlotException;
import com.example.e_ticketing.ticketing.excpetion.TimeSlotFullException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class GroupTicketBookingController {
    private final GroupBookingService groupBookingService;

    @PostMapping("/groupBooking")
    public ResponseEntity<?> StudentGroupTicket(@Valid
            @RequestBody GroupBookingDto bookingDto) {
        try {
            TicketGroupBookingResponse response = groupBookingService.bookGroupTicket(bookingDto);
            return ResponseEntity.ok(response);

        } catch (InvalidTicketTypeException | InvalidBookingException | InvalidTimeSlotException |
                 TimeSlotFullException ex) {
            // Known validation errors - return 400 Bad Request
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage())
            );

        } catch (Exception ex) {
            // Unexpected error - return 500 Internal Server Error
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred.")
            );
        }
    }


}
