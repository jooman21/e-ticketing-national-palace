package com.example.e_ticketing.ticketing.application.mapper;


import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;

import com.example.e_ticketing.ticketing.domain.entity.TicketType;

public class TicketTypeMapper {
    //Converts the TicketType entity (from the DB) into a TicketTypeDto to send back to the client.
    public static TicketTypeDto MapTicketTypeToTicketTypeDto(TicketType ticketType) {
        return TicketTypeDto.builder()
                .id(ticketType.getId())
                .name(ticketType.getName())
                .description(ticketType.getDescription())
                .image(ticketType.getImage())
                .targetPlaces(ticketType.getTargetPlaces())
                .isRecommended(ticketType.getIsRecommended())
                .available(ticketType.getAvailable())
                .createdAt(ticketType.getCreatedAt())
                .updatedAt(ticketType.getUpdatedAt())
                .build();
    }
    public static TicketType MapTicketTypeDtoToTicketType(TicketTypeDto dto) {
        // Converts the TicketTypeDto (received from the client) into a TicketType entity to save into the database.
        TicketType type = new TicketType();
        type.setId(dto.getId());
        type.setName(dto.getName());
        type.setDescription(dto.getDescription());
        type.setImage(dto.getImage());
        type.setTargetPlaces(dto.getTargetPlaces());
        type.setIsRecommended(dto.getIsRecommended());
        type.setAvailable(dto.getAvailable());
        type.setCreatedAt(dto.getCreatedAt());
        type.setUpdatedAt(dto.getUpdatedAt());
        return type;
    }
}



