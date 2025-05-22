package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.MaxTicketRequest;
import com.example.e_ticketing.ticketing.application.dto.TimeslotDto;
import com.example.e_ticketing.ticketing.application.service.TimeSlotService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeslots")
@RequiredArgsConstructor
public class TimeSotController {
    private final TimeSlotService timeSlotService;

    @PostMapping("/daily")
    public ResponseEntity<GenericResponse> createDailyTimeSlots(@RequestBody MaxTicketRequest request) {
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

}
