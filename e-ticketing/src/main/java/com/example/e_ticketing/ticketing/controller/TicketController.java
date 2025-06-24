package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.TicketDto;
import com.example.e_ticketing.ticketing.application.service.TicketService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import com.example.e_ticketing.ticketing.domain.entity.Ticket;
import com.example.e_ticketing.ticketing.domain.valueobject.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    // 1. Book Ticket
    @PostMapping("/book")
    public ResponseEntity<GenericResponse<TicketDto>> bookTicket(@RequestBody TicketDto ticketDto) {
        TicketDto bookedTicket = ticketService.bookTicket(ticketDto);
        return ResponseEntity.ok(
                new GenericResponse<>(true, "Ticket booked successfully", bookedTicket)
        );
    }

    // 2. Get Ticket by ID

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<TicketDto>> getTicketById(@PathVariable UUID id) {
        TicketDto ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(
                new GenericResponse<>(true, "Ticket fetched successfully", ticket)
        );
    }

    // 3. Get All Tickets
    @GetMapping
    public ResponseEntity<GenericResponse<List<TicketDto>>> getAllTickets() {
        List<TicketDto> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(
                new GenericResponse<>(true, "All tickets fetched successfully", tickets)
        );
    }

    // 4. Get Tickets by Status
    @GetMapping("/status")
    public ResponseEntity<GenericResponse<List<TicketDto>>> getTicketsByStatus(@RequestParam TicketStatus status) {
        List<TicketDto> tickets = ticketService.getTicketsByStatus(status);
        return ResponseEntity.ok(
                new GenericResponse<>(true, "Tickets by status fetched successfully", tickets)
        );
    }
}
