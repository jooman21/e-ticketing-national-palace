package com.example.e_ticketing.ticketing.application.serviceImpl;
import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;
import com.example.e_ticketing.ticketing.application.dto.VisitPlaceDto;
import com.example.e_ticketing.ticketing.application.mapper.TicketTypeMapper;
import com.example.e_ticketing.ticketing.application.mapper.VisitPlaceMapper;
import com.example.e_ticketing.ticketing.application.repository.*;
import com.example.e_ticketing.ticketing.application.service.TicketTypeService;
import com.example.e_ticketing.ticketing.domain.entity.*;
import com.example.e_ticketing.ticketing.excpetion.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;
    private final VisitPlaceRepository visitPlaceRepository;
    private final TicketPolicyRepository ticketPolicyRepository;
    private final VisitSchedulePlaceStatusRepository visitSchedulePlaceStatusRepository;
    private final VisitPlaceMapper visitPlaceMapper;


    @Override
    @Transactional
    public TicketTypeDto createTicketType(TicketTypeDto dto) {
        validateTicketTypeDto(dto);

        ticketTypeRepository.findByName(dto.getName()).ifPresent(existing -> {
            throw new TicketTypeAlreadyExistsException("Ticket Type '" + dto.getName() + "' already exists.");
        });

        // Load actual persisted VisitPlace entities
        List<VisitPlace> persistedVisitPlaces = new ArrayList<>();
        for (VisitPlaceDto visitPlaceDto : dto.getVisitPlaces()) {
            VisitPlace persisted = (VisitPlace) visitPlaceRepository.findByName(visitPlaceDto.getName())
                    .orElseThrow(() -> new VisitPlaceDoesNotExistException(
                            "Visit Place '" + visitPlaceDto.getName() + "' not found"));
            persistedVisitPlaces.add(persisted);
        }

        // Load persisted TicketPolicy
        TicketPolicy persistedTicketPolicy = ticketPolicyRepository.findById(dto.getTicketPolicyId())
                .orElseThrow(() -> new TicketPolicyNotFoundException("Ticket Policy with ID " + dto.getTicketPolicyId() + " not found."));

        // Map DTO to entity with policy
        TicketType entity = TicketTypeMapper.MapTicketTypeDtoToTicketType(dto, persistedTicketPolicy);

        // Override visit places with the fully fetched ones
        entity.setVisitPlaces(persistedVisitPlaces);

        // Set timestamps and default values
        entity.setCreatedAt(LocalDateTime.now());

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
        List<TicketType> ticketTypes = ticketTypeRepository.findAllByOrderByCreatedAtDesc();

        if (ticketTypes == null || ticketTypes.isEmpty()) {
            return List.of(); // or Collections.emptyList()
        }

        return ticketTypes.stream()
                .map(TicketTypeMapper::MapTicketTypeToTicketTypeDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<TicketTypeDto> getTicketTypesWithPartialAvailabilityImpact(LocalDate date) {
        System.out.println("🔍 Checking ticket types for date: " + date);

        // 1. Get schedule for the date
        List<VisitSchedule> schedules = visitSchedulePlaceStatusRepository.findByDateWithStatuses(date);
        System.out.println("📅 Found schedules for the date: " + schedules.size());

        if (schedules.isEmpty()) {
            System.out.println("⚠️ No schedules found — returning all ticket types as-is");
            return ticketTypeRepository.findAllByOrderByCreatedAtDesc()
                    .stream()
                    .map(TicketTypeMapper::MapTicketTypeToTicketTypeDto)
                    .collect(Collectors.toList());
        }

        VisitSchedule schedule = schedules.get(0); // assuming one schedule per date
        System.out.println("✅ Loaded VisitSchedule ID: " + schedule.getId() + ", isOpen: " + schedule.getIsOpen());

        // 2. Full closure
        if (Boolean.FALSE.equals(schedule.getIsOpen())) {
            System.out.println("🚫 Full closure detected — no ticket types available.");
            return List.of();
        }

        // 3. Collect IDs of visit places that are partially closed (isOpen == false)
        Set<UUID> closedVisitPlaceIds = schedule.getPlaceStatuses().stream()
                .filter(status -> Boolean.FALSE.equals(status.getIsOpen()))
                .peek(status -> System.out.println("❌ Partially closed: " + status.getVisitPlace().getName() + " (ID: " + status.getVisitPlace().getId() + ")"))
                .map(status -> status.getVisitPlace().getId())
                .collect(Collectors.toSet());

        System.out.println("🛑 Total closed visit places: " + closedVisitPlaceIds.size());

        // 4. Fetch all ticket types
        List<TicketType> allTicketTypes = ticketTypeRepository.findAllByOrderByCreatedAtDesc();
        System.out.println("🎟️ Fetched ticket types: " + allTicketTypes.size());

        // 5. Filter out closed visit places per ticket type and map them
        return allTicketTypes.stream()
                .map(ticketType -> {
                    System.out.println("\n➡️ Processing ticket type: " + ticketType.getName());

                    List<VisitPlace> availablePlaces = ticketType.getVisitPlaces().stream()
                            .filter(place -> !closedVisitPlaceIds.contains(place.getId()))
                            .peek(place -> System.out.println("   ✅ Included place: " + place.getName()))
                            .toList();

                    if (availablePlaces.isEmpty()) {
                        System.out.println("   ⚠️ Skipping ticket type '" + ticketType.getName() + "' — all visit places are closed");
                        return null;
                    }

                    TicketTypeDto dto = TicketTypeMapper.MapTicketTypeToTicketTypeDto(ticketType);
                    dto.setVisitPlaces(availablePlaces.stream()
                            .map(place -> {
                                VisitPlaceDto mapped = VisitPlaceMapper.MapVisitPlaceToVisitPlaceDto(place, closedVisitPlaceIds);
                                System.out.println("      🗂️ Mapped place: " + mapped.getName() + " | Available: " + mapped.getAvailable());
                                return mapped;
                            })
                            .toList());

                    return dto;
                })
                .filter(Objects::nonNull)
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
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new TicketTypeDoesNotExistException("Ticket type with ID " + id + " not found"));

        // Optional: validate fields of updatedDto
        validateTicketTypeDto(updatedDto);


        // Validate unique name if the name is being updated and changed
        if (updatedDto.getName() != null && !updatedDto.getName().equals(ticketType.getName())) {
            Optional<TicketType> existingWithName = ticketTypeRepository.findByName(updatedDto.getName());
            System.out.println("Checking for existing ticket type with name: " + updatedDto.getName());
            System.out.println("Found: " + existingWithName.isPresent());

            if (existingWithName.isPresent() && !existingWithName.get().getId().equals(ticketType.getId())) {
                throw new TicketTypeAlreadyExistsException("Ticket Type '" + updatedDto.getName() + "' already exists.");
            }
            ticketType.setName(updatedDto.getName());
        }

        if (updatedDto.getDescription() != null)
            ticketType.setDescription(updatedDto.getDescription());



        if (updatedDto.getIsRecommended() != null)
            ticketType.setIsRecommended(updatedDto.getIsRecommended());

        if (updatedDto.getAvailable() != null)
            ticketType.setAvailable(updatedDto.getAvailable());

        // Update VisitPlaces
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

        // Update TicketPolicy
        if (updatedDto.getTicketPolicyId() != null) {
            TicketPolicy persistedTicketPolicy = ticketPolicyRepository.findById(updatedDto.getTicketPolicyId())
                    .orElseThrow(() -> new RuntimeException("Ticket Policy with ID " + updatedDto.getTicketPolicyId() + " not found."));
            ticketType.setTicketPolicy(persistedTicketPolicy);
        }

        // Set update timestamp
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
