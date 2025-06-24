package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.TicketDto;
import com.example.e_ticketing.ticketing.domain.entity.Ticket;
import com.example.e_ticketing.ticketing.domain.valueobject.TicketStatus;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface TicketService {


    @Transactional
    TicketDto bookTicket(TicketDto ticketDto);

    TicketDto getTicketById(UUID id);

    List<TicketDto> getAllTickets();

    List<TicketDto> getTicketsByStatus(TicketStatus status);
}
