package com.example.e_ticketing.ticketing.application.mapper;


import com.example.e_ticketing.ticketing.application.dto.VisitPlaceDto;

import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component

public class VisitPlaceMapper {

    /**
     * Maps VisitPlace entity to VisitPlaceDto using DB-level availability (static).
     */
    public static VisitPlaceDto MapVisitPlaceToVisitPlaceDto(VisitPlace visitPlace) {
        return VisitPlaceDto.builder()
                .id(visitPlace.getId())
                .name(visitPlace.getName())
                .available(visitPlace.getIsAvailable()) // DB-level availability
                .createdAt(visitPlace.getCreatedAt())
                .updatedAt(visitPlace.getUpdatedAt())
                .build();
    }

    /**
     * Maps VisitPlace entity to VisitPlaceDto using dynamic availability override
     * based on a list of closed visit place IDs (per-date partial closure).
     */
    public static VisitPlaceDto MapVisitPlaceToVisitPlaceDto(VisitPlace visitPlace, Set<UUID> closedVisitPlaceIds) {
        boolean isTemporarilyClosed = closedVisitPlaceIds.contains(visitPlace.getId());

        return VisitPlaceDto.builder()
                .id(visitPlace.getId())
                .name(visitPlace.getName())
                .available(!isTemporarilyClosed) // override based on dynamic partial closure
                .createdAt(visitPlace.getCreatedAt())
                .updatedAt(visitPlace.getUpdatedAt())
                .build();
    }

    /**
     * Maps VisitPlaceDto to VisitPlace entity (for saving to DB).
     */
    public static VisitPlace MapVisitPlaceDtoToVisitPlace(VisitPlaceDto dto) {
        VisitPlace place = new VisitPlace();
        place.setId(dto.getId());
        place.setName(dto.getName());
        place.setIsAvailable(dto.getAvailable());
        place.setCreatedAt(dto.getCreatedAt());
        place.setUpdatedAt(dto.getUpdatedAt());
        return place;
    }

}
