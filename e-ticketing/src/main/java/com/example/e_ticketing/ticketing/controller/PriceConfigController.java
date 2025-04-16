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


    @PostMapping
    public List<PriceConfigDto> createPriceConfigs(@RequestBody List<PriceConfigDto> priceConfigDtos) {
        return priceConfigService.createPriceConfigs(priceConfigDtos);
    }
}
