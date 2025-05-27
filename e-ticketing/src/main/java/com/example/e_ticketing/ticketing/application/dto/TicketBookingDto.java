package com.example.e_ticketing.ticketing.application.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TicketBookingDto {
    private UUID ticketTypeId;
    private UUID ticketPolicyId;
    private LocalDate date;
   // private UUID visitScheduleId;
    private UUID timeSlotId;
    private VisitorDto visitorDto;
}
