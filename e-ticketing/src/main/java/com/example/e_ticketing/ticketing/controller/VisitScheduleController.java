package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.UnavailableDateRangeRequest;
import com.example.e_ticketing.ticketing.application.dto.UnavailableDateRequest;
import com.example.e_ticketing.ticketing.application.dto.UnavailableDatesRequest;
import com.example.e_ticketing.ticketing.application.service.VisitScheduleService;
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



    @PostMapping("/{visitPlaceId}/unavailable")
    public ResponseEntity<Void> markDateAsUnavailable(
            @PathVariable UUID visitPlaceId,
             @RequestBody UnavailableDateRequest request) {

        visitScheduleService.markDateAsUnavailable(visitPlaceId, request.getDate(), request.getReasonForClosing());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{visitPlaceId}/unavailable-range")
    public ResponseEntity<Void> markDateRangeAsUnavailable(
            @PathVariable UUID visitPlaceId,
            @RequestBody UnavailableDateRangeRequest request) {

        visitScheduleService.markDateRangeAsUnavailable(
                visitPlaceId, request.getStartDate(), request.getEndDate(), request.getReasonForClosing());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{visitPlaceId}/unavailable-dates")
    public ResponseEntity<Void> markMultipleDatesAsUnavailable(
            @PathVariable UUID visitPlaceId,
             @RequestBody UnavailableDatesRequest request) {

        visitScheduleService.markMultipleDatesAsUnavailable(
                visitPlaceId, request.getDates(), request.getReasonForClosing());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{visitPlaceId}/closed-dates")
    public ResponseEntity<List<LocalDate>> getClosedDates(
            @PathVariable UUID visitPlaceId) {

        List<LocalDate> closedDates = visitScheduleService.getClosedDatesByVisitPlace(visitPlaceId);
        return ResponseEntity.ok(closedDates);
    }




}
