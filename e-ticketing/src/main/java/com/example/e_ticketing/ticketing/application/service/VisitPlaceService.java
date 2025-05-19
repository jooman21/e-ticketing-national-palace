package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.VisitPlaceDto;

import java.util.List;

public interface VisitPlaceService {

    List<VisitPlaceDto> createVisitPlaces(List<VisitPlaceDto> dtoList);
}
