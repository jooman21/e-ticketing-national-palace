package com.example.e_ticketing.ticketing.application.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketPolicyDto {
    private UUID id;
    private Integer validityDays;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
