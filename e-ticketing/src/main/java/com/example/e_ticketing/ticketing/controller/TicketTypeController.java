package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;
import com.example.e_ticketing.ticketing.application.service.TicketTypeService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticketType")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;
    @PostMapping()
    public ResponseEntity<GenericResponse> createTicketType(@Valid  @RequestBody TicketTypeDto ticketTypeDto) {
        ticketTypeService.createTicketType(ticketTypeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponse(true, "Ticket type created successfully.", ticketTypeDto));
    }
    @GetMapping
    public ResponseEntity<GenericResponse<List<TicketTypeDto>>> getAllTicketTypes() {
        List<TicketTypeDto> ticketTypes = ticketTypeService.getAllTicketTypes();

        return ResponseEntity
                .ok(new GenericResponse<>(true, "Ticket types fetched successfully.", ticketTypes));
    }


    @GetMapping("/partial-availability")
    public ResponseEntity<List<TicketTypeDto>> getPartiallyImpactedTicketTypes(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TicketTypeDto> impactedTickets = ticketTypeService.getTicketTypesWithPartialAvailabilityImpact(date);
        return ResponseEntity.ok(impactedTickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<TicketTypeDto>> getTicketTypeById(@PathVariable @Valid  UUID id) {
        TicketTypeDto dto = ticketTypeService.getTicketTypeById(id);
        return ResponseEntity.ok(new GenericResponse<>(true, "Ticket type fetched successfully.", dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GenericResponse<TicketTypeDto>> updateTicketType(
            @PathVariable UUID id,
            @Valid
            @RequestBody TicketTypeDto updatedDto) {

        TicketTypeDto dto = ticketTypeService.updateTicketType(id, updatedDto);
        return ResponseEntity.ok(
                new GenericResponse<>(true, "Ticket type updated successfully.", dto)
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deactivateTicketType( @PathVariable @Valid UUID id) {
        ticketTypeService.deactivateTicketType(id);
        return ResponseEntity.ok(new GenericResponse<>(true, "Ticket type deactivated successfully.", null));
    }

}
