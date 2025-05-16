package com.example.e_ticketing.ticketing.application.serviceImpl;
import com.example.e_ticketing.ticketing.application.dto.TicketTypeDto;
import com.example.e_ticketing.ticketing.application.mapper.TicketTypeMapper;
import com.example.e_ticketing.ticketing.application.repository.TicketTypeRepository;
import com.example.e_ticketing.ticketing.application.service.TicketTypeService;
import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import com.example.e_ticketing.ticketing.excpetion.InvalidTicketTypeException;
import com.example.e_ticketing.ticketing.excpetion.TicketTypeAlreadyExistsException;
import com.example.e_ticketing.ticketing.excpetion.TicketTypeDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;

    @Override
    public TicketTypeDto createTicketType(TicketTypeDto dto) {
        validateTicketTypeDto(dto);

        ticketTypeRepository.findByName(dto.getName()).ifPresent(existing -> {
            throw new TicketTypeAlreadyExistsException("Ticket Type '" + dto.getName() + "' already exists.");
        });

        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setAvailable(true);

        TicketType entity = TicketTypeMapper.MapTicketTypeDtoToTicketType(dto);
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
        ticketTypeRepository.save(ticketType);
    }

    @Override
    public TicketTypeDto updateTicketType(UUID id, TicketTypeDto updatedDto) {
        TicketType ticketType = (TicketType) ticketTypeRepository.findById(id)
                .orElseThrow(() -> new TicketTypeDoesNotExistException("Ticket type with ID " + id + " not found"));

        // Optional: re-validate updated name and description
        validateTicketTypeDto(updatedDto);

        ticketType.setName(updatedDto.getName());
        ticketType.setDescription(updatedDto.getDescription());
        ticketType.setImage(updatedDto.getImage());
        ticketType.setIsRecommended(updatedDto.getIsRecommended());
        ticketType.setTargetPlaces(updatedDto.getTargetPlaces());
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
