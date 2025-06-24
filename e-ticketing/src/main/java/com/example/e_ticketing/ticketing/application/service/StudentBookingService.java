package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.StudentGroupBookingDto;
import com.example.e_ticketing.ticketing.application.response.TicketGroupBookingResponse;
import jakarta.transaction.Transactional;

public interface StudentBookingService {
    @Transactional
    TicketGroupBookingResponse bookStudentGroupTicket(StudentGroupBookingDto dto);
}
