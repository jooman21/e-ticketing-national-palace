package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;
import com.example.e_ticketing.ticketing.application.service.TicketTypeService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticketType")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;
    @PostMapping()
    public ResponseEntity<GenericResponse> createTicketType(@RequestBody TicketTypeDto ticketTypeDto) {
        ticketTypeService.createTicketType(ticketTypeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponse(true, "Ticket type created successfully."));
    }
    @GetMapping
    public ResponseEntity<GenericResponse<List<TicketTypeDto>>> getAllTicketTypes() {
        List<TicketTypeDto> ticketTypes = ticketTypeService.getAllTicketTypes();

        return ResponseEntity
                .ok(new GenericResponse<>(true, "Ticket types fetched successfully.", ticketTypes));
    }


    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<TicketTypeDto>> getTicketTypeById(@PathVariable UUID id) {
        TicketTypeDto dto = ticketTypeService.getTicketTypeById(id);
        return ResponseEntity.ok(new GenericResponse<>(true, "Ticket type fetched successfully.", dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GenericResponse<TicketTypeDto>> updateTicketType(
            @PathVariable UUID id,
            @RequestBody TicketTypeDto updatedDto) {

        TicketTypeDto dto = ticketTypeService.updateTicketType(id, updatedDto);
        return ResponseEntity.ok(
                new GenericResponse<>(true, "Ticket type updated successfully.", dto)
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deactivateTicketType(@PathVariable UUID id) {
        ticketTypeService.deactivateTicketType(id);
        return ResponseEntity.ok(new GenericResponse<>(true, "Ticket type deactivated successfully.", null));
    }

}
