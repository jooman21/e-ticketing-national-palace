package com.example.e_ticketing.ticketing.application.mapper;


import com.example.e_ticketing.ticketing.application.dto.VisitPlaceDto;

import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;

public class VisitPlaceMapper {

        //Converts the Visit Place entity (from the DB) into a VisitPlaceDto to send back to the client.
        public static VisitPlaceDto MapVisitPlaceToVisitPlaceDto(VisitPlace visitPlace) {
            return VisitPlaceDto.builder()
                    .id(visitPlace.getId())
                    .name(visitPlace.getName())
                    .available(visitPlace.getIsAvailable())
                    .createdAt(visitPlace.getCreatedAt())
                    .updatedAt(visitPlace.getUpdatedAt())
                    .build();
        }
        public static VisitPlace MapVisitPlaceDtoToVisitPlace(VisitPlaceDto dto) {
            // Converts the VisitPlaceDto (received from the client) into a VisitPlace entity to save into the database.
            VisitPlace place = new VisitPlace();
            place.setId(dto.getId());
            place.setName(dto.getName());
            place.setIsAvailable(dto.getAvailable());
            place.setCreatedAt(dto.getCreatedAt());
            place.setUpdatedAt(dto.getUpdatedAt());
            return place;
        }
    }

