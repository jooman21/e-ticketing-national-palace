package com.example.e_ticketing.ticketing.application.mapper;

import com.example.e_ticketing.ticketing.application.dto.TicketDto;
import com.example.e_ticketing.ticketing.application.dto.TicketRequestDto;
import com.example.e_ticketing.ticketing.domain.entity.Ticket;
import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import com.example.e_ticketing.ticketing.domain.entity.TimeSlot;
import com.example.e_ticketing.ticketing.domain.entity.VisitSchedule;
import com.example.e_ticketing.ticketing.domain.valueobject.TicketStatus;
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
        dto.setTicketPolicyId(ticket.);
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

    public Ticket MapTicketDtoToEntity(TicketRequestDto requestDto, TicketType ticketType, VisitSchedule schedule, TimeSlot slot) {
        Ticket ticket = new Ticket();
        ticket.setTicketType(ticketType);
        ticket.setVisitSchedule(schedule);
        ticket.setTimeSlot(slot);
        ticket.setTicketStatus(TicketStatus.VALID);
        ticket.setIssuedAt(LocalDateTime.now());
        ticket.setExpiresAt(schedule.getDate().atTime(slot.getEndTime()));
        return ticket;
    }
}
