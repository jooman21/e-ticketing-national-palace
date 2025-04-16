package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.PriceConfigDto;

import java.util.List;

public interface PriceConfigService {
//    List<PriceConfigDto> createPriceConfigs(PriceConfigDto priceConfigDto);

    List<PriceConfigDto> createPriceConfigs(List<PriceConfigDto> priceConfigDtos);
}
