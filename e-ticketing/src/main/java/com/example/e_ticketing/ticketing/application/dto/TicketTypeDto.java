package com.example.e_ticketing.ticketing.application.dto;



import com.example.e_ticketing.ticketing.domain.valueobject.TicketCategory;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private Boolean isRecommended;
    private TicketCategory ticketCategory;
    private Boolean available;
    private UUID ticketPolicyId;
    private List<VisitPlaceDto> visitPlaces = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
