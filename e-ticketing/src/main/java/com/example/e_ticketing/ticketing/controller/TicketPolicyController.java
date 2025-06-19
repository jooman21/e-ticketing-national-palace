package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.TicketPolicyDto;
import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;
import com.example.e_ticketing.ticketing.application.service.TicketPolicyService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ticket-policy")
@RequiredArgsConstructor
public class TicketPolicyController {
    private final TicketPolicyService ticketPolicyService;

    @PostMapping
    public ResponseEntity<GenericResponse<TicketPolicyDto>> createPolicy(@RequestBody TicketPolicyDto dto) {
        TicketPolicyDto created = ticketPolicyService.createPolicy(dto);
        return ResponseEntity.ok(new GenericResponse<>(true, "TicketPolicy created successfully", created));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<GenericResponse<TicketPolicyDto>> updatePolicy(@PathVariable UUID id,
                                                                         @RequestBody TicketPolicyDto dto) {
        TicketPolicyDto updated = ticketPolicyService.updatePolicy(id, dto);
        return ResponseEntity.ok(new GenericResponse<>(true, "TicketPolicy updated successfully", updated));
    }


    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<TicketPolicyDto>> getPolicy(@PathVariable UUID id) {
        TicketPolicyDto policy = ticketPolicyService.getPolicy(id);
        return ResponseEntity.ok(new GenericResponse<>(true, "TicketPolicy fetched successfully", policy));
    }
    @GetMapping
    public ResponseEntity<GenericResponse<List<TicketPolicyDto>>>getAllTicketPolicy() {
        List<TicketPolicyDto> ticketPolicy = ticketPolicyService.getAllPolicies();

            return ResponseEntity.ok(new GenericResponse<>(true, "TicketPolicy Fetched Successfully", ticketPolicy));
    }


    @DeleteMapping("/remove/{id}")
    public ResponseEntity<GenericResponse<String>> deletePolicy(@PathVariable UUID id) {
        ticketPolicyService.deletePolicy(id);
        return ResponseEntity.ok(new GenericResponse<>(true, "TicketPolicy deleted successfully", null));
    }
}
