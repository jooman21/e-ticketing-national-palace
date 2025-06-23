package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.TicketDto;
import com.example.e_ticketing.ticketing.domain.entity.Ticket;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface TicketService {




    @Transactional
    Ticket bookTicket(TicketDto ticketDto);

    TicketDto getTicketById(UUID id);

    List<TicketDto> getAllTickets();
}
