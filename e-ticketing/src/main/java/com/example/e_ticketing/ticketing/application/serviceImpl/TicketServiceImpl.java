package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.TicketDto;
import com.example.e_ticketing.ticketing.application.mapper.TicketMapper;
import com.example.e_ticketing.ticketing.application.repository.*;
import com.example.e_ticketing.ticketing.application.service.TicketService;
import com.example.e_ticketing.ticketing.application.service.VisitorService;
import com.example.e_ticketing.ticketing.domain.entity.*;
import com.example.e_ticketing.ticketing.domain.valueobject.TicketStatus;
import com.example.e_ticketing.ticketing.excpetion.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl  implements TicketService {
    private final VisitorService visitorService;
    private final TicketRepository ticketRepository;
    private final VisitScheduleRepository visitScheduleRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QueueEntryRepository queueEntryRepository;
    private final TicketPolicyRepository ticketPolicyRepository;
    private final TicketMapper ticketMapper;


    @Transactional
    @Override
    public Ticket bookTicket(TicketDto ticketDto) {
        // 1. Handle visitor (create or fetch existing)
        Visitor visitor = visitorService.handleVisitor(ticketDto.getVisitor());

        // 2. Fetch visit schedule
        VisitSchedule visitSchedule = visitScheduleRepository.findById(ticketDto.getVisitScheduleId())
                .orElseThrow(() -> new VisitScheduleNotFoundException("Visit Schedule not found with ID: " + ticketDto.getVisitScheduleId()));

        if (!visitSchedule.getIsOpen()) {
            throw new InvalidBookingException("Tickets cannot be booked for a closed day.");
        }

        // 3. Fetch time slot
        TimeSlot timeSlot = timeSlotRepository.findById(ticketDto.getTimeSlotId())
                .orElseThrow(() -> new InvalidTimeSlotException("Time slot not found"));

        // 4. Validate capacity
        int totalBooked = ticketRepository.countByTimeSlotAndVisitSchedule_Date(timeSlot, visitSchedule.getDate())
                + queueEntryRepository.countByTimeSlotAndVisitSchedule_Date(timeSlot, visitSchedule.getDate());

        if (totalBooked >= timeSlot.getMaxTickets()) {
            throw new TimeSlotFullException("Selected time slot is fully booked.");
        }

        // 5. Fetch ticket type
        TicketType ticketType = ticketTypeRepository.findById(ticketDto.getTicketTypeId())
                .orElseThrow(() -> new InvalidTicketTypeException("Ticket type not found"));

        // 6. Get policy from ticket type
        TicketPolicy policy = ticketType.getTicketPolicy();
        if (policy == null) {
            throw new TicketPolicyNotFoundException("No ticket policy attached to ticket type.");
        }

        // 7. Update DTO with issuedAt, expiresAt, ticket status
        ticketDto.setIssuedAt(LocalDateTime.now());
        ticketDto.setExpiresAt(LocalDateTime.now().plusDays(policy.getValidityDays()));
        ticketDto.setTicketStatus(TicketStatus.PENDING); // default before payment

        // 8. Map to entity and save
        Ticket ticket = ticketMapper.MapTicketDTOtoEntity(ticketDto, visitor, ticketType, visitSchedule, timeSlot);
        return ticketRepository.save(ticket);
    }

    @Override
    public TicketDto getTicketById(UUID id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id));
        return ticketMapper.MapTicketEntityToTicketDto(ticket);
    }

    @Override
    public List<TicketDto> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(TicketMapper::MapTicketEntityToTicketDto)
                .collect(Collectors.toList());
    }




}
