//package com.example.e_ticketing.ticketing.application.serviceImpl;
//
//import com.example.e_ticketing.ticketing.application.dto.PriceConfigForStudentDto;
//import com.example.e_ticketing.ticketing.application.mapper.PriceConfigForStudentMapper;
//import com.example.e_ticketing.ticketing.application.repository.PriceConfigRepository;
//import com.example.e_ticketing.ticketing.application.repository.TicketTypeRepository;
//import com.example.e_ticketing.ticketing.application.service.PriceConfigForStudentService;
//import com.example.e_ticketing.ticketing.domain.entity.PriceConfig;
//import com.example.e_ticketing.ticketing.domain.entity.TicketType;
//import com.example.e_ticketing.ticketing.domain.valueobject.Currency;
//import com.example.e_ticketing.ticketing.domain.valueobject.StudentType;
//import com.example.e_ticketing.ticketing.excpetion.InvalidPriceConfigException;
//import com.example.e_ticketing.ticketing.excpetion.PriceConfigAlreadyExistsException;
//import com.example.e_ticketing.ticketing.excpetion.PriceConfigDoesNotFoundException;
//import com.example.e_ticketing.ticketing.excpetion.TicketTypeDoesNotExistException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class PriceConfigForStudentServiceImpl implements PriceConfigForStudentService {
//    private final PriceConfigRepository priceConfigRepository;
//    private final TicketTypeRepository ticketTypeRepository;
//
//    @Override
//    public PriceConfigForStudentDto createPriceConfigForStudent(PriceConfigForStudentDto dto) {
//        validatePriceConfigDto(dto);
//
//        TicketType ticketType = getTicketTypeByName(dto.getName());
//
//        if (priceConfigRepository.existsByTicketTypeAndStudentType(ticketType, dto.getStudentType())) {
//            throw new PriceConfigAlreadyExistsException(
//                    "Price config already exists for ticket type '" + ticketType.getName() + "' and student type '" + dto.getStudentType() + "'");
//        }
//
//        fillDefaults(dto);
//
//        PriceConfig saved = priceConfigRepository.save(
//                PriceConfigForStudentMapper.MapPriceConfigForStudentDtoToPriceConfigForStudent(dto, ticketType)
//        );
//
//        return PriceConfigForStudentMapper.MapPriceConfigForStudentToPriceConfigForStudentDto(saved);
//    }
//
//    @Override
//    public List<PriceConfigForStudentDto> getAllPriceConfigs() {
//        return priceConfigRepository.findAll().stream()
//                .map(PriceConfigForStudentMapper::MapPriceConfigForStudentToPriceConfigForStudentDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public PriceConfigForStudentDto getPriceConfigForStudentById(UUID id) {
//        return PriceConfigForStudentMapper.MapPriceConfigForStudentToPriceConfigForStudentDto(getPriceConfigById(id));
//    }
//
//    @Override
//    public PriceConfigForStudentDto updatePriceConfig(UUID id, PriceConfigForStudentDto dto) {
//        validatePriceConfigDto(dto);
//
//        PriceConfig existing = getPriceConfigById(id);
//        TicketType ticketType = getTicketTypeByName(dto.getName());
//
//        // Check if a different PriceConfig with same TicketType & StudentType exists
//        List<PriceConfig> duplicates = priceConfigRepository.findByStudentTypeAndTicketType(dto.getStudentType(), ticketType);
//        boolean exists = duplicates.stream().anyMatch(pc -> !pc.getId().equals(id));
//
//        if (exists) {
//            throw new PriceConfigAlreadyExistsException("Price configuration with this ticket type and student type already exists.");
//        }
//
//
//        // Update fields
//        existing.setTicketType(ticketType);
//        existing.setCurrency(dto.getCurrency());
//        existing.setStudentType(dto.getStudentType());
//        existing.setPrice(dto.getPrice());
//        existing.setActive(Boolean.TRUE.equals(dto.getActive())); // defaults to true if null
//        existing.setUpdatedAt(LocalDateTime.now());
//
//        PriceConfig updated = priceConfigRepository.save(existing);
//        return PriceConfigForStudentMapper.MapPriceConfigForStudentToPriceConfigForStudentDto(updated);
//    }
//
//    @Override
//    public void deletePriceConfigForStudent(UUID id) {
//        PriceConfig config = getPriceConfigById(id);
//        config.setActive(false);
//        config.setUpdatedAt(LocalDateTime.now());
//        priceConfigRepository.save(config);
//    }
//
//    // ---------------------------
//    // 🔁 Reusable Private Methods
//    // ---------------------------
//
//    private void validatePriceConfigDto(PriceConfigForStudentDto dto) {
//        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
//            throw new TicketTypeDoesNotExistException("Ticket type name must not be null or empty.");
//        }
//
//        if (dto.getCurrency() == null) {
//            throw new InvalidPriceConfigException("Currency must not be null.");
//        }
//
//        if (dto.getStudentType() == null) {
//            throw new InvalidPriceConfigException("Student type must not be null.");
//        }
//
//        if ((dto.getStudentType() == StudentType.KG_TO_12 || dto.getStudentType() == StudentType.COLLEGE)
//                && dto.getCurrency() != Currency.ETB) {
//            throw new InvalidPriceConfigException("Student ticket types must use ETB currency only.");
//        }
//
//        if (dto.getPrice() == null || dto.getPrice() <= 0) {
//            throw new InvalidPriceConfigException("Price must be a positive number.");
//        }
//    }
//
//    private TicketType getTicketTypeByName(String name) {
//        return ticketTypeRepository.findByName(name)
//                .orElseThrow(() -> new TicketTypeDoesNotExistException("Ticket Type '" + name + "' not found"));
//    }
//
//    private PriceConfig getPriceConfigById(UUID id) {
//        return priceConfigRepository.findById(id)
//                .orElseThrow(() -> new PriceConfigDoesNotFoundException("Price config with ID " + id + " not found."));
//    }
//
//    private void fillDefaults(PriceConfigForStudentDto dto) {
//        dto.setCreatedAt(LocalDateTime.now());
//        if (dto.getActive() == null) {
//            dto.setActive(true);
//        }
//    }
//}
