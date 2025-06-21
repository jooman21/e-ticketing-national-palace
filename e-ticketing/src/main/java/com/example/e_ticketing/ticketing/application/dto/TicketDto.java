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
    private String ticketTypeName;

    private UUID visitScheduleId;
    private UUID timeSlotId;

    private TicketStatus ticketStatus;

    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;

    private String qrCode;

    private VisitorDto visitor;
}
