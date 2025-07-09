package com.example.e_ticketing.ticketing.application.dto;


import com.example.e_ticketing.ticketing.domain.valueobject.Residency;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupBookingDto {
    private UUID ticketTypeId;
    private LocalDate visitDate;
    private UUID timeSlotId;
    private Residency residency;
    private String visitorType;
    private int quantity;
    private VisitorDto representative;
}
