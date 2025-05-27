package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.TicketBookingDto;
import com.example.e_ticketing.ticketing.application.dto.TicketDto;
import com.example.e_ticketing.ticketing.application.mapper.TicketMapper;
import com.example.e_ticketing.ticketing.application.service.TicketService;
import com.example.e_ticketing.ticketing.domain.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

//    /**
//     * Book a ticket for a given schedule and time slot
//     * @param ticketDto contains visitScheduleId, timeSlotId, ticketTypeId, and ticketPolicyId
//     * @return the booked ticket info
//     */

    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@RequestBody TicketBookingDto ticketBookingDto) {
        Ticket ticket = ticketService.bookTicket(ticketBookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }
}
