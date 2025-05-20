package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.VisitPlaceDto;
import com.example.e_ticketing.ticketing.application.mapper.VisitPlaceMapper;
import com.example.e_ticketing.ticketing.application.repository.VisitPlaceRepository;
import com.example.e_ticketing.ticketing.application.service.VisitPlaceService;
import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;
import com.example.e_ticketing.ticketing.excpetion.InvalidVisitPlaceException;
import com.example.e_ticketing.ticketing.excpetion.TicketTypeDoesNotExistException;
import com.example.e_ticketing.ticketing.excpetion.VisitPlaceAlreadyExistsException;
import com.example.e_ticketing.ticketing.excpetion.VisitPlaceDoesNotExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

            VisitPlace visitPlace = VisitPlaceMapper.MapVisitPlaceDtoToVisitPlace(dto);

            if (visitPlace.getIsAvailable() == null) {
                visitPlace.setIsAvailable(false);
            }

            visitPlace.setCreatedAt(LocalDateTime.now());

            VisitPlace saved = visitPlaceRepository.save(visitPlace);
            createdPlaces.add(VisitPlaceMapper.MapVisitPlaceToVisitPlaceDto(saved));
        }

        return createdPlaces;
    }


    private void validateVisitPlaceDto(VisitPlaceDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new InvalidVisitPlaceException("Visitable Place  name must not be null or empty.");
        }

    }

    @Override
    public VisitPlaceDto getVisitPlaceById(UUID id) {
        VisitPlace visitPlace = (VisitPlace) visitPlaceRepository.findById(id)
                .orElseThrow(() -> new VisitPlaceDoesNotExistException("Visit Place with ID " + id + " not found."));
        return VisitPlaceMapper.MapVisitPlaceToVisitPlaceDto(visitPlace);
    }

    @Override
    public List<VisitPlaceDto> getAllVisitPlaces() {
        List<VisitPlace> visitPlaces = visitPlaceRepository.findAll();
        return visitPlaces.stream()
                .map(VisitPlaceMapper::MapVisitPlaceToVisitPlaceDto)
                .collect(Collectors.toList());
    }


    @Override
    public VisitPlaceDto updateVisitPlace(UUID id, VisitPlaceDto updatedDto) {
        VisitPlace visitPlace = (VisitPlace) visitPlaceRepository.findById(id)
                .orElseThrow(() -> new TicketTypeDoesNotExistException("Ticket type with ID " + id + " not found"));

        // Optional: validate if needed
        validateVisitPlaceDto(updatedDto);

        if (updatedDto.getName() != null)
            visitPlace.setName(updatedDto.getName());

       ;

        if (updatedDto.getAvailable() != null)
            visitPlace.setIsAvailable(updatedDto.getAvailable());





        visitPlace.setUpdatedAt(LocalDateTime.now());

        VisitPlace saved = visitPlaceRepository.save(visitPlace);
        return VisitPlaceMapper.MapVisitPlaceToVisitPlaceDto(saved);
    }

    @Override
    @Transactional
    public void deleteVisitPlace(UUID id) {
        VisitPlace visitPlace = (VisitPlace) visitPlaceRepository.findById(id)
                .orElseThrow(() -> new VisitPlaceDoesNotExistException("Visit Place with ID " + id + " not found."));
        visitPlace.setIsAvailable(false);
        visitPlaceRepository.save(visitPlace);
    }




}
