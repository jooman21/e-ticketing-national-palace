package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.PriceConfigDto;
import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;
import com.example.e_ticketing.ticketing.application.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
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
    public TicketTypeDto createTicketType(@RequestBody TicketTypeDto ticketTypeDto) {
        return ticketTypeService.createTicketType(ticketTypeDto);
    }
}
