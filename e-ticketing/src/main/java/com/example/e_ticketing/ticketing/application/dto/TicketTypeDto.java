package com.example.e_ticketing.ticketing.application.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketTypeDto {
    private UUID id;
    private String name;
    private String description;
    private Boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
