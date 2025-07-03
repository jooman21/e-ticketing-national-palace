package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.MaxTicketRequest;
import com.example.e_ticketing.ticketing.application.dto.TimeSlotAvailabilityDto;
import com.example.e_ticketing.ticketing.application.dto.TimeslotDto;
import com.example.e_ticketing.ticketing.application.service.TimeSlotService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/timeslots")
@RequiredArgsConstructor
public class TimeSotController {
    private final TimeSlotService timeSlotService;

    @PostMapping("/daily")
    public ResponseEntity<GenericResponse> createDailyTimeSlots(@Valid  @RequestBody MaxTicketRequest request) {
        List<TimeslotDto> slots = timeSlotService.createDailyTimeSlots(request.getMaxTickets());

        GenericResponse response = new GenericResponse(
                true,
                "Daily time slots created successfully.",
                slots
        );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/active")
    public ResponseEntity<GenericResponse> getAllActiveTimeSlots() {
        List<TimeslotDto> slots = timeSlotService.getAllActiveTimeSlots();

        GenericResponse response = new GenericResponse(
                true,
                "Active time slots fetched successfully.",
                slots
        );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/availability")
    public ResponseEntity<GenericResponse> getAvailableTimeSlots(@RequestParam @Valid  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TimeSlotAvailabilityDto> availability = timeSlotService.getAvailableTimeSlotsForDay(date);
        GenericResponse response = new GenericResponse(true, "Time slot availability fetched.", availability);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/max-tickets")
    public ResponseEntity<GenericResponse> updateMaxTickets(
            @PathVariable("id") UUID id,
            @Valid @RequestBody MaxTicketRequest request
    ) {
        TimeslotDto updatedSlot = timeSlotService.updateMaxTicketsForTimeSlot(id, request.getMaxTickets());

        GenericResponse response = new GenericResponse(
                true,
                "Time slot max tickets updated successfully.",
                updatedSlot
        );

        return ResponseEntity.ok(response);
    }


}
