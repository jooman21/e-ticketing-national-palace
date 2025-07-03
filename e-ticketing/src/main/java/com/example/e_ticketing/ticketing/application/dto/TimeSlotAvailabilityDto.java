package com.example.e_ticketing.ticketing.application.dto;

import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlotAvailabilityDto {

    private UUID id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maxTickets;
    private Integer currentTickets;
    private Boolean isActive;   // from entity
    private Boolean isAvailable;  // computed at runtime

    // Add this:
    private Integer remainingTickets;

    public Integer getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(Integer remainingTickets) {
        this.remainingTickets = remainingTickets;
    }
}
