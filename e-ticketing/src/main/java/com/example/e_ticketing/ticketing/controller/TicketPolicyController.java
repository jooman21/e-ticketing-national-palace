package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.TicketPolicyDto;
import com.example.e_ticketing.ticketing.application.service.TicketPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/ticket-policy")
@RequiredArgsConstructor
public class TicketPolicyController {
    private final TicketPolicyService ticketPolicyService;

    @PostMapping
    public ResponseEntity<TicketPolicyDto> createPolicy(@RequestBody TicketPolicyDto dto) {
        return ResponseEntity.ok(ticketPolicyService.createPolicy(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TicketPolicyDto> updatePolicy(@PathVariable UUID id,
                                                        @RequestBody TicketPolicyDto dto) {
        return ResponseEntity.ok(ticketPolicyService.updatePolicy(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketPolicyDto> getPolicy(@PathVariable UUID id) {
        return ResponseEntity.ok(ticketPolicyService.getPolicy(id));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable UUID id) {
        ticketPolicyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}
