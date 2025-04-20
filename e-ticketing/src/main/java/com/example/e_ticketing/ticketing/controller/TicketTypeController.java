package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;
import com.example.e_ticketing.ticketing.application.service.TicketTypeService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticketType")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;
    @PostMapping()
    public ResponseEntity<GenericResponse> createTicketType(@RequestBody TicketTypeDto ticketTypeDto) {
        ticketTypeService.createTicketType(ticketTypeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponse(true, "Ticket type created successfully."));
    }
}
