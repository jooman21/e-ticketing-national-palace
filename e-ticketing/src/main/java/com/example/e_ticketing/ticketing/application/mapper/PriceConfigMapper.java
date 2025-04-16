package com.example.e_ticketing.ticketing.application.mapper;

import com.example.e_ticketing.ticketing.application.dto.PriceConfigDto;
import com.example.e_ticketing.ticketing.domain.entity.PriceConfig;
import com.example.e_ticketing.ticketing.domain.entity.TicketType;

public class PriceConfigMapper {
    //Converts the PriceConfig entity (from the DB) into a PriceConfigDto to send back to the client.
    public static PriceConfigDto MapPriceConfigToPriceConfigDto(PriceConfig priceConfig) {
        return PriceConfigDto.builder()
                .id(priceConfig.getId())
                .name(priceConfig.getTicketType() != null ? priceConfig.getTicketType().getName() : null)
                .residency(priceConfig.getResidency())
                .currency(priceConfig.getCurrency())
                .price(priceConfig.getPrice())
                .active(priceConfig.getActive())
                .createdAt(priceConfig.getCreatedAt())
                .updatedAt(priceConfig.getUpdatedAt())
                .build();
    }

    public static PriceConfig MapPriceConfigDtoToPriceConfig(PriceConfigDto dto, TicketType ticketType) {
       // Converts the PriceConfigDto (received from the client) into a PriceConfig entity to save into the database.
        PriceConfig config = new PriceConfig();
        config.setId(dto.getId());
        config.setTicketType(ticketType);
        config.setResidency(dto.getResidency());
        config.setCurrency(dto.getCurrency());
        config.setPrice(dto.getPrice());
        config.setActive(dto.getActive());
        config.setCreatedAt(dto.getCreatedAt());
        config.setUpdatedAt(dto.getUpdatedAt());
        return config;
    }
}
