package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.TicketDto;
import com.example.e_ticketing.ticketing.application.repository.*;
import com.example.e_ticketing.ticketing.application.service.TicketService;
import com.example.e_ticketing.ticketing.domain.entity.Ticket;
import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import com.example.e_ticketing.ticketing.domain.entity.TimeSlot;
import com.example.e_ticketing.ticketing.domain.entity.VisitSchedule;
import com.example.e_ticketing.ticketing.domain.valueobject.TicketStatus;
import com.example.e_ticketing.ticketing.excpetion.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl  implements TicketService {
    private final TicketRepository ticketRepository;
    private final VisitScheduleRepository visitScheduleRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QueueEntryRepository queueEntryRepository;

    @Transactional
    @Override
    public Ticket bookTicket(TicketDto ticketDto) {
        // 1. Fetch VisitSchedule by ID
        VisitSchedule visitSchedule = visitScheduleRepository.findById(ticketDto.getVisitScheduleId())
                .orElseThrow(() -> new VisitScheduleNotFoundException("Visit Schedule not found"));



        // 2. Check if VisitSchedule is open
        if (!visitSchedule.getIsOpen()) {
            throw new InvalidBookingException("Tickets cannot be booked for a closed day.");
        }

        // 3. Fetch TimeSlot
        TimeSlot timeSlot = timeSlotRepository.findById(ticketDto.getTimeSlotId())
                .orElseThrow(() -> new InvalidTimeSlotException("Time slot not found"));

        // 4. Count existing tickets + queue for this timeSlot & schedule date
        int ticketCount = ticketRepository.countByTimeSlotAndVisitSchedule_Date(timeSlot, visitSchedule.getDate());
        int queueCount = queueEntryRepository.countByTimeSlotAndVisitSchedule_Date(timeSlot, visitSchedule.getDate());
        int total = ticketCount + queueCount;

        if (total >= timeSlot.getMaxTickets()) {
            throw new TimeSlotFullException("Selected time slot is fully booked.");
        }

        // 5. Fetch TicketType
        TicketType ticketType = (TicketType) ticketTypeRepository.findById(ticketDto.getTicketTypeId())
                .orElseThrow(() -> new InvalidTicketTypeException("Ticket type not found"));

        // 6. Create and save Ticket
        Ticket ticket = new Ticket();
        ticket.setTicketType(ticketType);
        ticket.setVisitSchedule(visitSchedule);
        ticket.setTimeSlot(timeSlot);
        ticket.setTicketStatus(TicketStatus.VALID); // assuming enum exists
        ticket.setIssuedAt(LocalDateTime.now());
       // LocalDateTime expiresAt = issuedAt.plusDays(ticketPolicy.getValidityDays());

        return ticketRepository.save(ticket);
    }

}
