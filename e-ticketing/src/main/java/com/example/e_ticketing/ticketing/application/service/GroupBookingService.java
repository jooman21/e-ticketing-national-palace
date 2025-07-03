package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.GroupBookingDto;

import com.example.e_ticketing.ticketing.application.response.TicketGroupBookingResponse;
import jakarta.transaction.Transactional;

public interface GroupBookingService {


    @Transactional
    TicketGroupBookingResponse bookGroupTicket(GroupBookingDto dto);
}
