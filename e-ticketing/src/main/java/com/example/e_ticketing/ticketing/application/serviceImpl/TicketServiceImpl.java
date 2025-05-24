package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.TicketDto;
import com.example.e_ticketing.ticketing.application.mapper.TicketMapper;
import com.example.e_ticketing.ticketing.application.repository.*;
import com.example.e_ticketing.ticketing.application.service.TicketService;
import com.example.e_ticketing.ticketing.domain.entity.*;
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
    private final TicketPolicyRepository ticketPolicyRepository;
    private final TicketMapper ticketMapper;

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

        // 🧠 Expiration logic
        TicketPolicy policy = ticketPolicyRepository.findById(ticketDto.getTicketPolicyId())
                .orElseThrow(() -> new TicketPolicyNotFoundException("Ticket policy not found"));


        // 5. Create Ticket
        Ticket ticket = ticketMapper.MapTicketDtoToEntity(ticketDto, ticketType, visitSchedule, timeSlot, policy);

        return ticketRepository.save(ticket);
    }

}
