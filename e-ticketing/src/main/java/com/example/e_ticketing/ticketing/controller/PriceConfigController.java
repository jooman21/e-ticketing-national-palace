package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.PriceConfigDto;
import com.example.e_ticketing.ticketing.application.service.PriceConfigService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/priceConfig")
public class PriceConfigController {
    private final PriceConfigService priceConfigService;


    @PostMapping("/local")
    public ResponseEntity<GenericResponse> createLocalPriceConfig(@RequestBody PriceConfigDto priceConfigDto) {
         priceConfigService.createPriceConfig(priceConfigDto);
         return ResponseEntity
                 .status(HttpStatus.CREATED)
                 .body(new GenericResponse(true, "Price Configuration created for local Resident"));
    }

    @PostMapping("/international")
    public ResponseEntity<GenericResponse> createInternationalPriceConfig(@RequestBody PriceConfigDto priceConfigDto) {
         priceConfigService.createPriceConfig(priceConfigDto);
        return ResponseEntity
                 .status(HttpStatus.CREATED)
                .body(new GenericResponse(true, "Price Configuration created for International  Resident"));
    }

}
