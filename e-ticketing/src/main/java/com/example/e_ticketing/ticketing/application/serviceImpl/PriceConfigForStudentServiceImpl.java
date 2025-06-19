package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.PriceConfigDto;
import com.example.e_ticketing.ticketing.application.dto.PriceConfigForStudentDto;
import com.example.e_ticketing.ticketing.application.mapper.PriceConfigForStudentMapper;
import com.example.e_ticketing.ticketing.application.repository.PriceConfigRepository;
import com.example.e_ticketing.ticketing.application.repository.TicketTypeRepository;
import com.example.e_ticketing.ticketing.application.service.PriceConfigForStudentService;
import com.example.e_ticketing.ticketing.domain.entity.PriceConfig;
import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import com.example.e_ticketing.ticketing.domain.valueobject.Currency;
import com.example.e_ticketing.ticketing.domain.valueobject.Residency;
import com.example.e_ticketing.ticketing.domain.valueobject.StudentType;
import com.example.e_ticketing.ticketing.excpetion.InvalidPriceConfigException;
import com.example.e_ticketing.ticketing.excpetion.PriceConfigAlreadyExistsException;
import com.example.e_ticketing.ticketing.excpetion.TicketTypeDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PriceConfigForStudentServiceImpl implements PriceConfigForStudentService {
    private final PriceConfigRepository priceConfigRepository;
    private final TicketTypeRepository ticketTypeRepository;

    @Override
    public PriceConfigDto createPriceConfigForStudent(PriceConfigForStudentDto dto) {
        validatePriceConfigDto(dto);

        TicketType ticketType = (TicketType) ticketTypeRepository.findByName(dto.getName())
                .orElseThrow(() -> new TicketTypeDoesNotExistException(
                        "Ticket Type '" + dto.getName() + "' not found"));

        StudentType studentType = dto.getStudentType();

        // Check for duplicate config
        if (priceConfigRepository.existsByTicketTypeAndStudentType(ticketType, studentType)) {
            throw new PriceConfigAlreadyExistsException("Price config for student already exists for this  ticket type '" +
                    ticketType.getName() + "' and residency '" + studentType + "'");
        }

        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        if (dto.getActive() == null) {
            dto.setActive(false);
        }

        PriceConfig entity = PriceConfigForStudentMapper.MapPriceConfigForStudentDtoToPriceConfigForStudent(dto, ticketType);
        PriceConfig saved = priceConfigRepository.save(entity);

        return PriceConfigForStudentMapper.MapPriceConfigForStudentToPriceConfigForStudentDto(saved);
    }


    private void validatePriceConfigDto(PriceConfigForStudentDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new TicketTypeDoesNotExistException("Ticket type name must not be null or empty.");
        }

        if (dto.getCurrency() == null) {
            throw new InvalidPriceConfigException("Currency must not be null.");
        }

        if (dto.getStudentType() == null) {
            throw new InvalidPriceConfigException("Student type must not be null.");
        }

        // ✅ Enforce ETB for all student types
        if ((dto.getStudentType() == StudentType.KG_TO_12 || dto.getStudentType() == StudentType.COLLEGE)
                && dto.getCurrency() != Currency.ETB) {
            throw new InvalidPriceConfigException("Student ticket types must use ETB currency only.");
        }

        if (dto.getPrice() == null || dto.getPrice() <= 0) {
            throw new InvalidPriceConfigException("Price must be a positive number.");
        }
    }


}
