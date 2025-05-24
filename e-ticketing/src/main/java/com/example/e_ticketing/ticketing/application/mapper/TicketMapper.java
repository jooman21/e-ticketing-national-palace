package com.example.e_ticketing.ticketing.application.mapper;

import com.example.e_ticketing.ticketing.application.dto.TicketDto;
import com.example.e_ticketing.ticketing.application.dto.TicketRequestDto;
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

    public Ticket MapTicketDtoToEntity(TicketDto ticketDto, TicketType ticketType, VisitSchedule schedule, TimeSlot slot, TicketPolicy policy) {
        Ticket ticket = new Ticket();
        ticket.setTicketType(ticketType);
        ticket.setVisitSchedule(schedule);
        ticket.setTimeSlot(slot);
        ticket.setTicketStatus(TicketStatus.VALID);
        ticket.setIssuedAt(LocalDateTime.now());
        LocalDateTime issuedAt = LocalDateTime.now();
        ticket.setIssuedAt(issuedAt);

        if (policy != null && policy.getValidityDays() != null) {
            LocalDateTime expiresAt = issuedAt.plusDays(policy.getValidityDays());
            ticket.setExpiresAt(expiresAt);
        } else {
            // Handle the case where policy is missing (optional: throw or set a default expiration)
            throw new TicketPolicyNotFoundException("Ticket policy or its validityDays must not be null");
        }

        return ticket;
    }
}
