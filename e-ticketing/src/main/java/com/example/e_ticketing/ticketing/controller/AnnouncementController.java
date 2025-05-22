package com.example.e_ticketing.ticketing.controller;


import com.example.e_ticketing.ticketing.application.dto.AnnouncementDto;
import com.example.e_ticketing.ticketing.application.mapper.AnnouncementMapper;
import com.example.e_ticketing.ticketing.application.service.AnnouncementPartialClosure;
import com.example.e_ticketing.ticketing.application.service.AnnouncementTotalClosure;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import com.example.e_ticketing.ticketing.domain.valueobject.AnnouncementType;
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
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementPartialClosure announcementPartialClosure;
    private final AnnouncementTotalClosure announcementTotalClosure;

    @PostMapping("/total-closure")
    public ResponseEntity<GenericResponse> createTotalClosure(@RequestBody AnnouncementDto dto) {
        dto.setAnnouncementType(AnnouncementType.TOTAL_CLOSURE);

        List<Announcement> announcements = announcementTotalClosure.create(dto);
        List<AnnouncementDto> responseDtos = announcements.stream()
                .map(AnnouncementMapper::toDto)
                .toList();

        GenericResponse response = new GenericResponse(
                true,
                "Total closure announcements created successfully.",
                responseDtos
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/partial-availability")
    public ResponseEntity<GenericResponse> createPartialAvailabilityAnnouncement(@RequestBody AnnouncementDto dto) {
        dto.setAnnouncementType(AnnouncementType.PARTIAL_AVAILABILITY);

        List<Announcement> announcements = announcementPartialClosure.createPartialAvailabilityAnnouncement(dto);
        List<AnnouncementDto> responseDtos = announcements.stream()
                .map(AnnouncementMapper::toDto)
                .toList();

        GenericResponse response = new GenericResponse(
                true,
                "Partial availability announcements created successfully.",
                responseDtos
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/partial-availability")
    public ResponseEntity<GenericResponse> getPartialAvailabilityAnnouncements(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) UUID visitPlaceId
    ) {
        List<AnnouncementDto> announcements = announcementPartialClosure.getPartialAvailabilityAnnouncements(date, visitPlaceId);

        GenericResponse response = new GenericResponse(
                true,
                "Partial availability announcements fetched successfully.",
                announcements
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/total-closure")
    public ResponseEntity<GenericResponse> getTotalClosureAnnouncements(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<AnnouncementDto> announcements = announcementTotalClosure.getTotalClosureAnnouncements(date);

        GenericResponse response = new GenericResponse(
                true,
                "Total Closure announcements fetched successfully.",
                announcements
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<GenericResponse> getAllAnnouncements() {
        List<AnnouncementDto> announcements = announcementTotalClosure.getAllAnnouncements();

        GenericResponse response = new GenericResponse(
                true,
                "All announcements fetched successfully.",
                announcements
        );

        return ResponseEntity.ok(response);
    }

}

