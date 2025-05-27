package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.TicketBookingDto;
import com.example.e_ticketing.ticketing.application.mapper.TicketMapper;
import com.example.e_ticketing.ticketing.application.repository.*;
import com.example.e_ticketing.ticketing.application.service.TicketService;
import com.example.e_ticketing.ticketing.application.service.VisitorService;
import com.example.e_ticketing.ticketing.domain.entity.*;
import com.example.e_ticketing.ticketing.excpetion.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Ticket bookTicket(TicketBookingDto ticketBookingDto) {
        Visitor visitor = visitorService.handleVisitor(ticketBookingDto.getVisitorDto()); // use correct method


        VisitSchedule visitSchedule = visitScheduleRepository.findByDate(ticketBookingDto.getDate())
                .orElseThrow(() -> new VisitScheduleNotFoundException("Visit Schedule not found for the given date"));

        if (!visitSchedule.getIsOpen()) {
            throw new InvalidBookingException("Tickets cannot be booked for a closed day.");
        }

        TimeSlot timeSlot = timeSlotRepository.findById(ticketBookingDto.getTimeSlotId())
                .orElseThrow(() -> new InvalidTimeSlotException("Time slot not found"));

        int total = ticketRepository.countByTimeSlotAndVisitSchedule_Date(timeSlot, visitSchedule.getDate()) +
                queueEntryRepository.countByTimeSlotAndVisitSchedule_Date(timeSlot, visitSchedule.getDate());

        if (total >= timeSlot.getMaxTickets()) {
            throw new TimeSlotFullException("Selected time slot is fully booked.");
        }

        TicketType ticketType = (TicketType) ticketTypeRepository.findById(ticketBookingDto.getTicketTypeId())
                .orElseThrow(() -> new InvalidTicketTypeException("Ticket type not found"));

        TicketPolicy policy = ticketPolicyRepository.findById(ticketBookingDto.getTicketPolicyId())
                .orElseThrow(() -> new TicketPolicyNotFoundException("Ticket policy not found"));

        Ticket ticket = ticketMapper.mapBookingRequestToTicket(
                ticketBookingDto,
                ticketType,
                visitSchedule,
                timeSlot,
                policy,
                visitor
        );

        return ticketRepository.save(ticket);
    }


}
