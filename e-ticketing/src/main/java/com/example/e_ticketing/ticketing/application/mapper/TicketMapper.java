package com.example.e_ticketing.ticketing.application.mapper;

import com.example.e_ticketing.ticketing.application.dto.TicketBookingDto;
import com.example.e_ticketing.ticketing.application.dto.TicketDto;
import com.example.e_ticketing.ticketing.application.dto.TicketRequestDto;
import com.example.e_ticketing.ticketing.application.dto.VisitorDto;
import com.example.e_ticketing.ticketing.domain.entity.*;
import com.example.e_ticketing.ticketing.domain.valueobject.TicketStatus;
import com.example.e_ticketing.ticketing.excpetion.TicketPolicyNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TicketMapper {
    public TicketDto MapTicketEntityToTicketDto(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());

        // TicketType info
        dto.setTicketTypeId(ticket.getTicketType().getId());
        dto.setTicketTypeName(ticket.getTicketType().getName());
        // TicketPolicy ID (assuming it's fetched via TicketType → TicketPolicy)
        if (ticket.getTicketType().getTicketPolicy() != null) {
            dto.setTicketPolicyId(ticket.getTicketType().getTicketPolicy().getId());
        }
        // VisitSchedule info
        dto.setVisitScheduleId(ticket.getVisitSchedule().getId());
        dto.setVisitDate(ticket.getVisitSchedule().getDate());

        // TimeSlot info
        dto.setTimeSlotId(ticket.getTimeSlot().getId());
        dto.setStartTime(ticket.getTimeSlot().getStartTime());
        dto.setEndTime(ticket.getTimeSlot().getEndTime());

        // Ticket status and time info
        dto.setTicketStatus(ticket.getTicketStatus());
        dto.setIssuedAt(ticket.getIssuedAt());
        dto.setExpiresAt(ticket.getExpiresAt());

        return dto;
    }
    // Map from booking request + dependencies to Ticket entity
    public Ticket mapBookingRequestToTicket(
            TicketBookingDto ticketBookingDto,
            TicketType ticketType,
            VisitSchedule schedule,
            TimeSlot slot,
            TicketPolicy policy,
            Visitor visitor
    ) {
        LocalDateTime issuedAt = LocalDateTime.now();

        return Ticket.builder()
                .ticketType(ticketType)
                .visitSchedule(schedule)
                .timeSlot(slot)
                .ticketStatus(TicketStatus.VALID)
                .issuedAt(issuedAt)
                .expiresAt(issuedAt.plusDays(policy.getValidityDays()))
                .visitor(visitor)
                .build();
    }
}
