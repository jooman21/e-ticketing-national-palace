package com.example.e_ticketing.ticketing.controller;


import com.example.e_ticketing.ticketing.application.dto.AnnouncementDto;
import com.example.e_ticketing.ticketing.application.mapper.AnnouncementMapper;
import com.example.e_ticketing.ticketing.application.service.AnnouncementService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;


    @PostMapping
    public ResponseEntity<GenericResponse> createAnnouncements(@RequestBody AnnouncementDto dto) {
        List<Announcement> announcements = announcementService.createAnnouncement(dto);
        List<AnnouncementDto> responseDtos = announcements.stream()
                .map(AnnouncementMapper::toDto)
                .toList();

        GenericResponse response = new GenericResponse(
                true,
                "Announcements created successfully.",
                responseDtos
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
