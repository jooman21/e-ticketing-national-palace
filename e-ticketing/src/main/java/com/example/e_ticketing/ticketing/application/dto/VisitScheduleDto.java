package com.example.e_ticketing.ticketing.application.dto;


import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitScheduleDto {
    private UUID id;
    private LocalDate date;
    private Boolean isOpen;
    private String reasonForClosing;
    private List<UUID> visitPlaceIds;
}
