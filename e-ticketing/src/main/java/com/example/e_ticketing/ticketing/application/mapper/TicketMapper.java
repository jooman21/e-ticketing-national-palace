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

    public static TicketDto MapTicketEntityToTicketDto(Ticket ticket) {
        if (ticket == null) return null;

        return TicketDto.builder()
                .id(ticket.getId())
                .ticketTypeId(ticket.getTicketType().getId())
                .ticketTypeName(ticket.getTicketType().getName())
                .visitScheduleId(ticket.getVisitSchedule().getId())
                .timeSlotId(ticket.getTimeSlot().getId())
                .ticketStatus(ticket.getTicketStatus())
                .issuedAt(ticket.getIssuedAt())
                .expiresAt(ticket.getExpiresAt())
                .qrCode(ticket.getQrCode())
                .visitor(VisitorMapper.MapVisitorEntityToVisitorDto(ticket.getVisitor()))
                .build();
    }

    public static Ticket MapTicketDTOtoEntity(TicketDto dto, Visitor visitor, TicketType ticketType, VisitSchedule visitSchedule, TimeSlot timeSlot) {
        if (dto == null) return null;

        return Ticket.builder()
                .ticketType(ticketType)
                .visitor(visitor)
                .visitSchedule(visitSchedule)
                .timeSlot(timeSlot)
                .ticketStatus(dto.getTicketStatus())
                .issuedAt(dto.getIssuedAt())
                .expiresAt(dto.getExpiresAt())
                .qrCode(dto.getQrCode())
                .build();
    }
}

