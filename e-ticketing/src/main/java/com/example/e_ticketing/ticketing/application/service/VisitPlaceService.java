package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.VisitPlaceDto;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface VisitPlaceService {

    List<VisitPlaceDto> createVisitPlaces(List<VisitPlaceDto> dtoList);

    VisitPlaceDto getVisitPlaceById(UUID id);

    List<VisitPlaceDto> getAllVisitPlaces();

    VisitPlaceDto updateVisitPlace(UUID id, VisitPlaceDto updatedDto);

    @Transactional
    void deleteVisitPlace(UUID id);
}
