package com.example.e_ticketing.ticketing.application.dto;

import com.example.e_ticketing.ticketing.domain.valueobject.StudentType;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupBookingDto {
    private UUID ticketTypeId;            // Student-specific ticket type
    private LocalDate visitDate;
    private UUID timeSlotId;
    private VisitorType studentType;
    private int quantity;                 // Number of students
}
