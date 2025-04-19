package com.example.e_ticketing.ticketing.application.dto;

import com.example.e_ticketing.ticketing.domain.entity.PriceConfig;
import com.example.e_ticketing.ticketing.domain.entity.Ticket;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketTypeDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
