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
    public List<PriceConfigDto> createPriceConfigs(List<PriceConfigDto> priceConfigDtos) {
        List<PriceConfigDto> allSavedConfigs = new ArrayList<>();

        for (PriceConfigDto dto : priceConfigDtos) {
            validatePriceConfigDto(dto);

            TicketType ticketType = (TicketType) ticketTypeRepository.findByName(dto.getName())
                    .orElseThrow(() -> new PriceConfigNotFoundException("Ticket Type '" + dto.getName() + "' not found"));

            List<Residency> residencies = List.of(Residency.LOCAL, Residency.INTERNATIONAL);

            for (Residency residency : residencies) {
                if (priceConfigRepository.existsByTicketTypeAndResidency(ticketType, residency)) {
                    throw new RuntimeException("Price config already exists for ticket type '" + ticketType.getName() + "' and residency '" + residency + "'");
                }

                PriceConfigDto configForResidency = PriceConfigDto.builder()
                        .name(ticketType.getName())
                        .residency(residency)
                        .currency(dto.getCurrency())
                        .price(dto.getPrice())
                        .active(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                PriceConfig entity = PriceConfigMapper.MapPriceConfigDtoToPriceConfig(configForResidency, ticketType);
                PriceConfig savedEntity = priceConfigRepository.save(entity);
                allSavedConfigs.add(PriceConfigMapper.MapPriceConfigToPriceConfigDto(savedEntity));
            }
        }

        return allSavedConfigs;
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
