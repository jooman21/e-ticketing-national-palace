package com.example.e_ticketing.ticketing.application.serviceImpl;
import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;
import com.example.e_ticketing.ticketing.application.dto.VisitPlaceDto;
import com.example.e_ticketing.ticketing.application.mapper.TicketTypeMapper;
import com.example.e_ticketing.ticketing.application.repository.TicketTypeRepository;
import com.example.e_ticketing.ticketing.application.repository.VisitPlaceRepository;
import com.example.e_ticketing.ticketing.application.service.TicketTypeService;
import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;
import com.example.e_ticketing.ticketing.excpetion.InvalidTicketTypeException;
import com.example.e_ticketing.ticketing.excpetion.TicketTypeAlreadyExistsException;
import com.example.e_ticketing.ticketing.excpetion.TicketTypeDoesNotExistException;
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
public class TicketTypeServiceImpl implements TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;
    private final VisitPlaceRepository visitPlaceRepository;

    @Override
    @Transactional
    public TicketTypeDto createTicketType(TicketTypeDto dto) {
        validateTicketTypeDto(dto);

        ticketTypeRepository.findByName(dto.getName()).ifPresent(existing -> {
            throw new TicketTypeAlreadyExistsException("Ticket Type '" + dto.getName() + "' already exists.");
        });

        // Load the actual persisted VisitPlace entities
        List<VisitPlace> persistedVisitPlaces = new ArrayList<>();
        for (VisitPlaceDto visitPlaceDto : dto.getVisitPlaces()) {
            VisitPlace persisted = (VisitPlace) visitPlaceRepository.findByName(visitPlaceDto.getName())
                    .orElseThrow(() -> new VisitPlaceDoesNotExistException(
                            "Visit Place '" + visitPlaceDto.getName() + "' not found"));
            persistedVisitPlaces.add(persisted);
        }

        // Set actual VisitPlace entities into a new TicketType instance (not into DTO)
        TicketType entity = TicketTypeMapper.MapTicketTypeDtoToTicketType(dto);
        entity.setVisitPlaces(persistedVisitPlaces);

        // Set timestamps and defaults
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        if (entity.getAvailable() == null) {
            entity.setAvailable(false);
        }

        if (entity.getIsRecommended() == null) {
            entity.setIsRecommended(false);
        }

        TicketType saved = ticketTypeRepository.save(entity);
        return TicketTypeMapper.MapTicketTypeToTicketTypeDto(saved);
    }

    @Override
    public List<TicketTypeDto> getAllTicketTypes() {
        return ticketTypeRepository.findAll()
                .stream()
                .map(TicketTypeMapper::MapTicketTypeToTicketTypeDto)
                .collect(Collectors.toList());
    }

    @Override
    public TicketTypeDto getTicketTypeById(UUID id) {
        TicketType ticketType = (TicketType) ticketTypeRepository.findById(id)
                .orElseThrow(() -> new TicketTypeDoesNotExistException("Ticket type with ID " + id + " not found"));
        return TicketTypeMapper.MapTicketTypeToTicketTypeDto(ticketType);
    }

    @Override
    public void deactivateTicketType(UUID id) {
        TicketType ticketType = (TicketType) ticketTypeRepository.findById(id)
                .orElseThrow(() -> new TicketTypeDoesNotExistException("Ticket type with ID " + id + " not found"));

        ticketType.setAvailable(false);
        ticketType.setUpdatedAt(LocalDateTime.now());

        TicketType saved = ticketTypeRepository.save(ticketType);

    }


    @Override
    public TicketTypeDto updateTicketType(UUID id, TicketTypeDto updatedDto) {
        TicketType ticketType = (TicketType) ticketTypeRepository.findById(id)
                .orElseThrow(() -> new TicketTypeDoesNotExistException("Ticket type with ID " + id + " not found"));

        // Optional: validate if needed
        validateTicketTypeDto(updatedDto);

        if (updatedDto.getName() != null)
            ticketType.setName(updatedDto.getName());

        if (updatedDto.getDescription() != null)
            ticketType.setDescription(updatedDto.getDescription());

        if (updatedDto.getImage() != null)
            ticketType.setImage(updatedDto.getImage());

        if (updatedDto.getIsRecommended() != null)
            ticketType.setIsRecommended(updatedDto.getIsRecommended());

        if (updatedDto.getAvailable() != null)
            ticketType.setAvailable(updatedDto.getAvailable());

        if (updatedDto.getVisitPlaces() != null) {
            List<VisitPlace> updatedVisitPlaces = new ArrayList<>();
            for (VisitPlaceDto visitPlaceDto : updatedDto.getVisitPlaces()) {
                VisitPlace persisted = (VisitPlace) visitPlaceRepository.findByName(visitPlaceDto.getName())
                        .orElseThrow(() -> new VisitPlaceDoesNotExistException(
                                "Visit Place '" + visitPlaceDto.getName() + "' not found"));
                updatedVisitPlaces.add(persisted);
            }
            ticketType.setVisitPlaces(updatedVisitPlaces);
        }


        ticketType.setUpdatedAt(LocalDateTime.now());

        TicketType saved = ticketTypeRepository.save(ticketType);
        return TicketTypeMapper.MapTicketTypeToTicketTypeDto(saved);
    }


    private void validateTicketTypeDto(TicketTypeDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new InvalidTicketTypeException("Ticket type name must not be null or empty.");
        }

        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
            throw new InvalidTicketTypeException("Ticket type description must not be null or empty.");
        }
    }
}
