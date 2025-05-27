package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.TicketBookingDto;
import com.example.e_ticketing.ticketing.application.dto.TicketDto;
import com.example.e_ticketing.ticketing.application.dto.TicketRequestDto;
import com.example.e_ticketing.ticketing.domain.entity.Ticket;
import jakarta.transaction.Transactional;

public interface TicketService {


    @Transactional
    Ticket bookTicket(TicketBookingDto ticketBookingDto);
}
