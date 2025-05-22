package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.UnavailableDateRangeRequest;
import com.example.e_ticketing.ticketing.application.dto.UnavailableDateRequest;
import com.example.e_ticketing.ticketing.application.dto.UnavailableDatesRequest;
import com.example.e_ticketing.ticketing.application.service.VisitScheduleService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/visit-schedules")
@RequiredArgsConstructor
public class VisitScheduleController {
    private final VisitScheduleService visitScheduleService;



    @GetMapping("/gate-closed-dates")
    public ResponseEntity<GenericResponse> getAllGateClosedDates() {
        List<LocalDate> closedDates = visitScheduleService.getAllGateClosedDates();

        GenericResponse response = new GenericResponse(
                true,
                "museum closed dates fetched successfully.",
                closedDates
        );

        return ResponseEntity.ok(response);
    }





}
