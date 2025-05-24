package com.example.e_ticketing.ticketing.application.dto;

import com.example.e_ticketing.ticketing.domain.valueobject.TicketStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto {
    private UUID id;
    private UUID ticketTypeId;
    private UUID ticketPolicyId;
    private String ticketTypeName;

    private UUID visitScheduleId;
    private LocalDate visitDate;

    private UUID timeSlotId;
    private LocalTime startTime;
    private LocalTime endTime;

    private TicketStatus ticketStatus;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
}
