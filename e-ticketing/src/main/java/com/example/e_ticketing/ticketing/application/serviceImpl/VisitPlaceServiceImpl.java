package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.VisitPlaceDto;
import com.example.e_ticketing.ticketing.application.mapper.VisitPlaceMapper;
import com.example.e_ticketing.ticketing.application.repository.VisitPlaceRepository;
import com.example.e_ticketing.ticketing.application.service.VisitPlaceService;
import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;
import com.example.e_ticketing.ticketing.excpetion.InvalidVisitPlaceException;
import com.example.e_ticketing.ticketing.excpetion.VisitPlaceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitPlaceServiceImpl implements VisitPlaceService {

private final VisitPlaceRepository visitPlaceRepository;
    @Override
    public List<VisitPlaceDto> createVisitPlaces(List<VisitPlaceDto> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            throw new InvalidVisitPlaceException("List of visit places cannot be null or empty.");
        }

        List<VisitPlaceDto> createdPlaces = new ArrayList<>();

        for (VisitPlaceDto dto : dtoList) {
            validateVisitPlaceDto(dto);

            visitPlaceRepository.findByName(dto.getName()).ifPresent(existing -> {
                throw new VisitPlaceAlreadyExistsException("Visitable place '" + dto.getName() + "' already exists.");
            });

            VisitPlace saved = visitPlaceRepository.save(VisitPlaceMapper.MapVisitPlaceDtoToVisitPlace(dto));
            createdPlaces.add(VisitPlaceMapper.MapVisitPlaceToVisitPlaceDto(saved));
        }

        return createdPlaces;
    }

    private void validateVisitPlaceDto(VisitPlaceDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new InvalidVisitPlaceException("Visitable Place  name must not be null or empty.");
        }

    }

}
