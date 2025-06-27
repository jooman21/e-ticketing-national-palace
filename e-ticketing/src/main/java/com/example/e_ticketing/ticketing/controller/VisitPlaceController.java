package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;
import com.example.e_ticketing.ticketing.application.dto.VisitPlaceDto;
import com.example.e_ticketing.ticketing.application.service.TicketTypeService;
import com.example.e_ticketing.ticketing.application.service.VisitPlaceService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visitPlace")
@CrossOrigin(origins = "http://localhost:5173")
public class VisitPlaceController {
    private final VisitPlaceService visitPlaceService;

    @PostMapping("/batch")
    public ResponseEntity<GenericResponse> createVisitPlaces(@Valid  @RequestBody List<VisitPlaceDto> visitPlaceDtos) {
        List<VisitPlaceDto> saved = visitPlaceService.createVisitPlaces(visitPlaceDtos);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponse(true, saved.size() + " VisitPlaces created successfully.", saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<VisitPlaceDto>> getVisitPlaceById( @PathVariable @Valid UUID id) {
        VisitPlaceDto dto = visitPlaceService.getVisitPlaceById(id);
        return ResponseEntity.ok(new GenericResponse<>(true, "Visit place fetched successfully.", dto));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<VisitPlaceDto>>> getAllVisitPlaces() {
        List<VisitPlaceDto> visitPlaces = visitPlaceService.getAllVisitPlaces();
        return ResponseEntity.ok(new GenericResponse<>(true, "Visit places fetched successfully.", visitPlaces));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<GenericResponse<VisitPlaceDto>> updateVisitPlace(

            @PathVariable UUID id,
            @RequestBody @Valid VisitPlaceDto updatedDto) {

        VisitPlaceDto visitPlace = visitPlaceService.updateVisitPlace(id, updatedDto);
        return ResponseEntity.ok(new GenericResponse<>(true, "Visit place updated successfully.", visitPlace));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteVisitPlace(@PathVariable @Valid  UUID id) {
        visitPlaceService.deleteVisitPlace(id);
        return ResponseEntity.ok(new GenericResponse<>(true, "Visit place deleted successfully.", null));
    }

}
