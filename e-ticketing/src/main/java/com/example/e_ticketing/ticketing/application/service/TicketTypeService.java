package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TicketTypeService {
    TicketTypeDto createTicketType(TicketTypeDto dto);

    List<TicketTypeDto> getAllTicketTypes();

    List<TicketTypeDto> getTicketTypesWithPartialAvailabilityImpact(LocalDate date);

    TicketTypeDto getTicketTypeById(UUID id);

    void deactivateTicketType(UUID id);

    TicketTypeDto updateTicketType(UUID id, TicketTypeDto updatedDto);
}
