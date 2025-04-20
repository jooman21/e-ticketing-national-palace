package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.PriceConfigDto;

import java.util.List;
import java.util.UUID;

public interface PriceConfigService {
    PriceConfigDto createPriceConfig(PriceConfigDto dto);

    List<PriceConfigDto> getAllPriceConfigs();

//    PriceConfigDto getPriceConfigById(Long id);


    PriceConfigDto getPriceConfigById(UUID id);

    PriceConfigDto updatePriceConfig(UUID id, PriceConfigDto dto);

    void deletePriceConfig(UUID id);
}
