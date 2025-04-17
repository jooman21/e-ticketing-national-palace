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

import com.example.e_ticketing.ticketing.excpetion.TicketTypeDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PriceConfigServiceImpl implements PriceConfigService {

    private final PriceConfigRepository priceConfigRepository;
    private final TicketTypeRepository ticketTypeRepository;



    @Override
    public PriceConfigDto createPriceConfig(PriceConfigDto dto) {
        validatePriceConfigDto(dto);

        TicketType ticketType = (TicketType) ticketTypeRepository.findByName(dto.getName())
                .orElseThrow(() -> new TicketTypeDoesNotExistException(
                        "Ticket Type '" + dto.getName() + "' not found"));

        Residency residency = dto.getResidency();

        // Check for duplicate config
        if (priceConfigRepository.existsByTicketTypeAndResidency(ticketType, residency)) {
            throw new RuntimeException("Price config already exists for ticket type '" +
                    ticketType.getName() + "' and residency '" + residency + "'");
        }

        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setActive(true);

        PriceConfig entity = PriceConfigMapper.MapPriceConfigDtoToPriceConfig(dto, ticketType);
        PriceConfig saved = priceConfigRepository.save(entity);

        return PriceConfigMapper.MapPriceConfigToPriceConfigDto(saved);
    }

    private void validatePriceConfigDto(PriceConfigDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket type name must not be null or empty.");
        }

        if (dto.getCurrency() == null) {
            throw new IllegalArgumentException("Currency must not be null.");
        }

        if (dto.getResidency() == null) {
            throw new IllegalArgumentException("Residency must not be null.");
        }

        if (dto.getResidency() == Residency.LOCAL && dto.getCurrency() != Currency.ETB) {
            throw new IllegalArgumentException("Local residency must use ETB currency.");
        }

        if (dto.getResidency() == Residency.INTERNATIONAL &&
                !(dto.getCurrency() == Currency.USD || dto.getCurrency() == Currency.EURO)) {
            throw new IllegalArgumentException("International residency must use USD or EURO currency.");
        }

        if (dto.getPrice() == null || dto.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be a positive number.");
        }
    }


}
