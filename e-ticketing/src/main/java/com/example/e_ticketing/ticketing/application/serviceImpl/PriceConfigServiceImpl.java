package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.PriceConfigDto;
import com.example.e_ticketing.ticketing.application.mapper.PriceConfigMapper;
import com.example.e_ticketing.ticketing.application.repository.PriceConfigRepository;
import com.example.e_ticketing.ticketing.application.repository.TicketTypeRepository;
import com.example.e_ticketing.ticketing.application.service.PriceConfigService;
import com.example.e_ticketing.ticketing.domain.entity.PriceConfig;
import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import com.example.e_ticketing.ticketing.domain.valueobject.Residency;
import com.example.e_ticketing.ticketing.excpetion.PriceConfigNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceConfigServiceImpl implements PriceConfigService {

    private final PriceConfigRepository priceConfigRepository;
    private final TicketTypeRepository ticketTypeRepository;

    @Override
    public List<PriceConfigDto> createPriceConfigs(PriceConfigDto priceConfigDto) {
        validatePriceConfigDto(priceConfigDto);

        // 1. Validate and fetch ticket type
        TicketType ticketType = (TicketType) ticketTypeRepository.findByName(priceConfigDto.getName())
                .orElseThrow(() -> new PriceConfigNotFoundException("Ticket Type '" + priceConfigDto.getName() + "' not found"));

        // 2. Create configs for both LOCAL and INTERNATIONAL
        List<Residency> residencies = List.of(Residency.LOCAL, Residency.INTERNATIONAL);
        List<PriceConfigDto> savedConfigs = new ArrayList<>();

        for (Residency residency : residencies) {
            // 3. Check for duplicates
            if (priceConfigRepository.existsByTicketTypeAndResidency(ticketType, residency)) {
                throw new RuntimeException("Price config already exists for ticket type '" + ticketType.getName() + "' and residency '" + residency + "'");
            }

            // 4. Build PriceConfigDto per residency
            PriceConfigDto configForResidency = PriceConfigDto.builder()
                    .name(ticketType.getName()) // always from the ticketType
                    .residency(residency)
                    .currency(priceConfigDto.getCurrency())
                    .price(priceConfigDto.getPrice())
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            // 5. Convert to entity and save
            PriceConfig priceConfigEntity = PriceConfigMapper.MapPriceConfigDtoToPriceConfig(configForResidency, ticketType);
            PriceConfig savedEntity = priceConfigRepository.save(priceConfigEntity);
            savedConfigs.add(PriceConfigMapper.MapPriceConfigToPriceConfigDto(savedEntity));
        }

        return savedConfigs;
    }

    private void validatePriceConfigDto(PriceConfigDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket type name must not be null or empty.");
        }

        if (dto.getCurrency() == null) {
            throw new IllegalArgumentException("Currency must not be null.");
        }

        if (dto.getPrice() == null || dto.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be a positive number.");
        }
    }
}
