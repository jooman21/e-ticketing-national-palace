package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.PriceConfigDto;
import com.example.e_ticketing.ticketing.application.mapper.PriceConfigMapper;
import com.example.e_ticketing.ticketing.application.repository.PriceConfigRepository;
import com.example.e_ticketing.ticketing.application.repository.TicketTypeRepository;
import com.example.e_ticketing.ticketing.application.service.PriceConfigService;
import com.example.e_ticketing.ticketing.domain.entity.PriceConfig;
import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import com.example.e_ticketing.ticketing.domain.valueobject.Currency;
import com.example.e_ticketing.ticketing.domain.valueobject.Residency;

import com.example.e_ticketing.ticketing.domain.valueobject.TicketCategory;
import com.example.e_ticketing.ticketing.excpetion.InvalidPriceConfigException;
import com.example.e_ticketing.ticketing.excpetion.PriceConfigAlreadyExistsException;
import com.example.e_ticketing.ticketing.excpetion.PriceConfigDoesNotFoundException;
import com.example.e_ticketing.ticketing.excpetion.TicketTypeDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PriceConfigServiceImpl implements PriceConfigService {

    private final PriceConfigRepository priceConfigRepository;
    private final TicketTypeRepository ticketTypeRepository;



    @Override
    public PriceConfigDto createPriceConfig(PriceConfigDto dto) {
        validatePriceConfigDto(dto);

        TicketType ticketType = ticketTypeRepository.findByName(dto.getName())
                .orElseThrow(() -> new TicketTypeDoesNotExistException(
                        "Ticket Type '" + dto.getName() + "' not found"));

        // ✅ Enforce visitorType rules based on ticket category
        if (ticketType.getTicketCategory() == TicketCategory.GROUP) {
            if (dto.getVisitorType() == null || dto.getVisitorType().trim().isEmpty()) {
                throw new InvalidPriceConfigException("Visitor type is required for group ticket pricing.");
            }
        } else if (ticketType.getTicketCategory() == TicketCategory.INDIVIDUAL) {
            if (dto.getVisitorType() != null && !dto.getVisitorType().trim().isEmpty()) {
                throw new InvalidPriceConfigException("Visitor type must not be provided for individual ticket pricing.");
            }
            // Optional: clear it if client sends it
            dto.setVisitorType(null);
        }

        Residency residency = dto.getResidency();

        // ✅ Check for duplicate config
        if (priceConfigRepository.existsByTicketTypeAndResidencyAndVisitorType(ticketType, residency, dto.getVisitorType())) {
            throw new PriceConfigAlreadyExistsException("Price config already exists for ticket type '" +
                    ticketType.getName() + "', residency '" + residency + "', and visitor type '" + dto.getVisitorType() + "'");
        }

        dto.setCreatedAt(LocalDateTime.now());
        if (dto.getActive() == null) {
            dto.setActive(true);
        }

        PriceConfig entity = PriceConfigMapper.MapPriceConfigDtoToPriceConfig(dto, ticketType);
        PriceConfig saved = priceConfigRepository.save(entity);

        return PriceConfigMapper.MapPriceConfigToPriceConfigDto(saved);
    }


    private void validatePriceConfigDto(PriceConfigDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new TicketTypeDoesNotExistException("Ticket type name must not be null or empty.");
        }

        if (dto.getCurrency() == null) {
            throw new InvalidPriceConfigException("Currency must not be null.");
        }

        if (dto.getResidency() == null) {
            throw new InvalidPriceConfigException("Residency must not be null.");
        }

        if (dto.getResidency() == Residency.LOCAL && dto.getCurrency() != Currency.ETB) {
            throw new InvalidPriceConfigException("Local residency must use ETB currency.");
        }

        if (dto.getResidency() == Residency.INTERNATIONAL &&
                !(dto.getCurrency() == Currency.USD || dto.getCurrency() == Currency.EURO)) {
            throw new InvalidPriceConfigException("International residency must use USD or EURO currency.");
        }

        if (dto.getPrice() == null || dto.getPrice() <= 0) {
            throw new InvalidPriceConfigException("Price must be a positive number.");
        }
    }


    @Override
    public List<PriceConfigDto> getAllPriceConfigs() {
        return priceConfigRepository.findAll()
                .stream()
                .map(PriceConfigMapper::MapPriceConfigToPriceConfigDto)
                .collect(Collectors.toList());
    }

    @Override
    public PriceConfigDto getPriceConfigById(UUID id) {
        PriceConfig config = priceConfigRepository.findById(id)
                .orElseThrow(() -> new PriceConfigDoesNotFoundException("Price config with ID " + id + " not found."));
        return PriceConfigMapper.MapPriceConfigToPriceConfigDto(config);
    }


    @Override
    public PriceConfigDto updatePriceConfig(UUID id, PriceConfigDto dto) {
        validatePriceConfigDto(dto);

        // Fetch existing config
        PriceConfig existing = priceConfigRepository.findById(id)
                .orElseThrow(() -> new PriceConfigDoesNotFoundException("Price config with ID " + id + " not found."));

        // Find the TicketType
        TicketType ticketType = ticketTypeRepository.findByName(dto.getName())
                .orElseThrow(() -> new TicketTypeDoesNotExistException("Ticket Type '" + dto.getName() + "' not found"));

        // Check for duplicate config (excluding current one)
        boolean duplicateExists = priceConfigRepository
                .findByTicketTypeAndResidencyAndVisitorType(ticketType, dto.getResidency(), dto.getVisitorType())
                .stream()
                .anyMatch(pc -> !pc.getId().equals(id));

        if (duplicateExists) {
            throw new PriceConfigAlreadyExistsException("Another config already exists with the same ticket type, residency, and visitor type.");
        }

        // Apply updates
        existing.setTicketType(ticketType);
        existing.setResidency(dto.getResidency());
        existing.setVisitorType(dto.getVisitorType());
        existing.setCurrency(dto.getCurrency());
        existing.setPrice(dto.getPrice());
        existing.setActive(Boolean.TRUE.equals(dto.getActive()));
        existing.setUpdatedAt(LocalDateTime.now());

        PriceConfig updated = priceConfigRepository.save(existing);
        return PriceConfigMapper.MapPriceConfigToPriceConfigDto(updated);
    }

    @Override
    public void deletePriceConfig(UUID id) {
        PriceConfig config = priceConfigRepository.findById(id)
                .orElseThrow(() -> new PriceConfigDoesNotFoundException("Price config with ID " + id + " not found."));

        config.setActive(false);
        config.setUpdatedAt(LocalDateTime.now());
        priceConfigRepository.save(config);
    }
}
