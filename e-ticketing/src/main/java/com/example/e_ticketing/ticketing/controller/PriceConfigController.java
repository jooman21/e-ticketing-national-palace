package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.PriceConfigDto;
import com.example.e_ticketing.ticketing.application.service.PriceConfigService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/priceConfig")
public class PriceConfigController {
    private final PriceConfigService priceConfigService;


    @PostMapping("/local")
    public ResponseEntity<GenericResponse> createLocalPriceConfig(@Valid  @RequestBody PriceConfigDto priceConfigDto) {
         priceConfigService.createPriceConfig(priceConfigDto);
         return ResponseEntity
                 .status(HttpStatus.CREATED)
                 .body(new GenericResponse(true, "Price Configuration created for local Resident"));
    }

    @PostMapping("/international")
    public ResponseEntity<GenericResponse> createInternationalPriceConfig( @Valid @RequestBody PriceConfigDto priceConfigDto) {
         priceConfigService.createPriceConfig(priceConfigDto);
        return ResponseEntity
                 .status(HttpStatus.CREATED)
                .body(new GenericResponse(true, "Price Configuration created for International  Resident"));
    }


    @GetMapping
    public ResponseEntity<List<PriceConfigDto>> getAllPriceConfigs() {
        return ResponseEntity.ok(priceConfigService.getAllPriceConfigs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceConfigDto> getPriceConfigById( @PathVariable @Valid UUID id) {
        return ResponseEntity.ok(priceConfigService.getPriceConfigById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> updatePriceConfig( @PathVariable @Valid UUID id,
                                                         @RequestBody PriceConfigDto dto) {
        priceConfigService.updatePriceConfig(id, dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GenericResponse (true, "Price config updated successfully."));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deletePriceConfig(@PathVariable @Valid  UUID id) {
        priceConfigService.deletePriceConfig(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GenericResponse (true, "Price config deactivated successfully."));
    }
}
