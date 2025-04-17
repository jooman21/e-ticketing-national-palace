package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.PriceConfigDto;
import com.example.e_ticketing.ticketing.application.service.PriceConfigService;
import com.example.e_ticketing.ticketing.domain.entity.PriceConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/priceConfig")
public class PriceConfigController {
    private final PriceConfigService priceConfigService;


    @PostMapping("/local")
    public PriceConfigDto createLocalPriceConfig(@RequestBody PriceConfigDto priceConfigDto) {
        return priceConfigService.createPriceConfig(priceConfigDto);
    }

    @PostMapping("/international")
    public PriceConfigDto createInternationalPriceConfig(@RequestBody PriceConfigDto priceConfigDto) {
        return priceConfigService.createPriceConfig(priceConfigDto);
    }
}
