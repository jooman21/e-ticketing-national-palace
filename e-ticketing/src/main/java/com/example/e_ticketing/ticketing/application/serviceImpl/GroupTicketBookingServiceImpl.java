package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.GroupBookingDto;
import com.example.e_ticketing.ticketing.application.mapper.TicketMapper;
import com.example.e_ticketing.ticketing.application.repository.*;
import com.example.e_ticketing.ticketing.application.response.TicketGroupBookingResponse;
import com.example.e_ticketing.ticketing.application.service.GroupBookingService;
import com.example.e_ticketing.ticketing.application.service.VisitorService;
import com.example.e_ticketing.ticketing.domain.entity.*;
import com.example.e_ticketing.ticketing.domain.valueobject.TicketCategory;
import com.example.e_ticketing.ticketing.domain.valueobject.TicketStatus;
import com.example.e_ticketing.ticketing.excpetion.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupTicketBookingServiceImpl implements GroupBookingService {
    private final VisitorService visitorService;
    private final TicketRepository ticketRepository;
    private final VisitScheduleRepository visitScheduleRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final PriceConfigRepository priceConfigRepository;


    @Transactional
    @Override
    public TicketGroupBookingResponse bookGroupTicket(GroupBookingDto dto) {
        // 1. Validate ticket type
        TicketType ticketType = ticketTypeRepository.findById(dto.getTicketTypeId())
                .orElseThrow(() -> new InvalidTicketTypeException("Ticket type not found"));

        // ✅ Enforce ticket type must be GROUP for group booking
        if (ticketType.getTicketCategory() != TicketCategory.GROUP) {
            throw new InvalidTicketTypeException("Selected ticket type does not support group booking.");
        }

        // ✅ Handle representative (required)
        if (dto.getRepresentative() == null) {
            throw new InvalidBookingException("Group representative information is required.");
        }
        Visitor representative = visitorService.handleVisitor(dto.getRepresentative());

        // 2. Check visit date open/closed
        Optional<VisitSchedule> visitScheduleOpt = visitScheduleRepository.findByDate(dto.getVisitDate());
        if (visitScheduleOpt.isPresent() && !visitScheduleOpt.get().getIsOpen()) {
            throw new InvalidBookingException("Selected date is closed.");
        }

        // 3. Check slot capacity
        TimeSlot slot = timeSlotRepository.findById(dto.getTimeSlotId())
                .orElseThrow(() -> new InvalidTimeSlotException("Time slot not found"));

        int currentBookings = ticketRepository.countByTimeSlotAndVisitDateAndTicketStatus(
                slot, dto.getVisitDate(), TicketStatus.PENDING);

        if (currentBookings + dto.getQuantity() > slot.getMaxTickets()) {
            throw new TimeSlotFullException("Not enough capacity for the selected time slot.");
        }

        // 4. Get matching price config using visitorType + residency
        PriceConfig priceConfig = priceConfigRepository
                .findByTicketTypeAndResidencyAndVisitorType(
                        ticketType,
                        dto.getResidency(),
                        dto.getVisitorType()
                )
                .orElseThrow(() -> new PriceConfigDoesNotFoundException(
                        "Price config not found for visitor type '" + dto.getVisitorType()
                                + "' with residency '" + dto.getResidency() + "'"));

        BigDecimal totalPrice = BigDecimal.valueOf(priceConfig.getPrice())
                .multiply(BigDecimal.valueOf(dto.getQuantity()));

        // 5. Create Tickets
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < dto.getQuantity(); i++) {
            Ticket ticket = Ticket.builder()
                    .ticketType(ticketType)
                    .visitDate(dto.getVisitDate())
                    .timeSlot(slot)
                    .ticketStatus(TicketStatus.PENDING)
                    .issuedAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusDays(ticketType.getTicketPolicy().getValidityDays()))
                    .visitor(representative) // ✅ Associate the representative
                    .build();

            tickets.add(ticket);
        }

        List<Ticket> savedTickets = ticketRepository.saveAll(tickets);

        // 6. Return response (optional: include representative info later if needed)
        return TicketGroupBookingResponse.builder()
                .ticketTypeName(ticketType.getName())
                .visitorType(dto.getVisitorType())
                .totalTickets(dto.getQuantity())
                .totalPrice(totalPrice)
                .tickets(savedTickets.stream().map(TicketMapper::MapTicketEntityToTicketDto).toList())
                .build();
    }


}
