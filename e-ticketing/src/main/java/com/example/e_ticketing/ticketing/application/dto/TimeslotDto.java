package com.example.e_ticketing.ticketing.application.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeslotDto {
    private UUID id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isActive;
    private int maxTickets;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
