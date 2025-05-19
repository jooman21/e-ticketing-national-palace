package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;
import com.example.e_ticketing.ticketing.application.dto.VisitPlaceDto;
import com.example.e_ticketing.ticketing.application.service.TicketTypeService;
import com.example.e_ticketing.ticketing.application.service.VisitPlaceService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
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
@RequestMapping("/visitPlace")
public class VisitPlaceController {
    private final VisitPlaceService visitPlaceService;

    @PostMapping("/batch")
    public ResponseEntity<GenericResponse> createVisitPlaces(@RequestBody List<VisitPlaceDto> visitPlaceDtos) {
        List<VisitPlaceDto> saved = visitPlaceService.createVisitPlaces(visitPlaceDtos);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponse(true, saved.size() + " VisitPlaces created successfully.", saved));
    }


}
