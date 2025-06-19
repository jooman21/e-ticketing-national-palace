package com.example.e_ticketing.ticketing.application.mapper;


import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;

import com.example.e_ticketing.ticketing.application.dto.VisitPlaceDto;
import com.example.e_ticketing.ticketing.domain.entity.TicketPolicy;
import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;

import java.util.List;
import java.util.stream.Collectors;




public class TicketTypeMapper {
    //Converts the TicketType entity (from the DB) into a TicketTypeDto to send back to the client.
    public static TicketTypeDto MapTicketTypeToTicketTypeDto(TicketType ticketType) {
        List<VisitPlaceDto> visitPlaceDtos = ticketType.getVisitPlaces()
                .stream()
                .map(visitPlace -> VisitPlaceDto.builder()
                        .id(visitPlace.getId())
                        .name(visitPlace.getName())
                        .build())
                .collect(Collectors.toList());

        return TicketTypeDto.builder()
                .id(ticketType.getId())
                .name(ticketType.getName())
                .description(ticketType.getDescription())
                .image(ticketType.getImage())
                .visitPlaces(visitPlaceDtos)
                .isRecommended(ticketType.getIsRecommended())
                .ticketPolicyId(ticketType.getTicketPolicy() != null ? ticketType.getTicketPolicy().getId() : null)
                .available(ticketType.getAvailable())
                .createdAt(ticketType.getCreatedAt())
                .updatedAt(ticketType.getUpdatedAt())
                .build();
    }

    public static TicketType MapTicketTypeDtoToTicketType(TicketTypeDto dto, TicketPolicy ticketPolicy) {
        // Converts the TicketTypeDto (received from the client) into a TicketType entity to save into the database.
        List<VisitPlace> visitPlaces = dto.getVisitPlaces()
                .stream()
                .map(dtoPlace -> {
                    VisitPlace place = new VisitPlace();
                    place.setId(dtoPlace.getId());
                    place.setName(dtoPlace.getName());
                    return place;
                })
                .collect(Collectors.toList());

        TicketType type = new TicketType();
        type.setId(dto.getId());
        type.setName(dto.getName());
        type.setDescription(dto.getDescription());
        type.setImage(dto.getImage());
        type.setVisitPlaces(visitPlaces);
        type.setIsRecommended(dto.getIsRecommended());
        // ✅ Set the mapped TicketPolicy
        type.setTicketPolicy(ticketPolicy);
        type.setAvailable(dto.getAvailable());
        type.setCreatedAt(dto.getCreatedAt());
        type.setUpdatedAt(dto.getUpdatedAt());
        return type;
    }
}
