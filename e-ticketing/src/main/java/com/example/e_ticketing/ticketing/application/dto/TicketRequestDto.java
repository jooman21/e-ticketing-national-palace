package com.example.e_ticketing.ticketing.application.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketRequestDto {
    private UUID ticketTypeId;
    private UUID visitScheduleId;
    private UUID timeSlotId;
}
