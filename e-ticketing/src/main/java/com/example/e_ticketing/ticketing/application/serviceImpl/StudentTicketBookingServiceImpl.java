//package com.example.e_ticketing.ticketing.application.serviceImpl;
//
//import com.example.e_ticketing.ticketing.application.dto.StudentGroupBookingDto;
//import com.example.e_ticketing.ticketing.application.mapper.TicketMapper;
//import com.example.e_ticketing.ticketing.application.repository.*;
//import com.example.e_ticketing.ticketing.application.response.TicketGroupBookingResponse;
//import com.example.e_ticketing.ticketing.application.service.StudentBookingService;
//import com.example.e_ticketing.ticketing.application.service.VisitorService;
//import com.example.e_ticketing.ticketing.domain.entity.*;
//import com.example.e_ticketing.ticketing.domain.valueobject.TicketStatus;
//import com.example.e_ticketing.ticketing.excpetion.*;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class StudentTicketBookingServiceImpl implements StudentBookingService {
//    private final TicketRepository ticketRepository;
//    private final VisitScheduleRepository visitScheduleRepository;
//    private final TimeSlotRepository timeSlotRepository;
//    private final TicketTypeRepository ticketTypeRepository;
//    private final PriceConfigRepository priceConfigRepository;
//    @Transactional
//    @Override
//    public TicketGroupBookingResponse bookStudentGroupTicket(StudentGroupBookingDto dto) {
//        // 1. Validate ticket type
//        TicketType ticketType = ticketTypeRepository.findById(dto.getTicketTypeId())
//                .orElseThrow(() -> new InvalidTicketTypeException("Ticket type not found"));
//
//        // 2. Check visit date open/closed
//        Optional<VisitSchedule> visitScheduleOpt = visitScheduleRepository.findByDate(dto.getVisitDate());
//        if (visitScheduleOpt.isPresent() && !visitScheduleOpt.get().getIsOpen()) {
//            throw new InvalidBookingException("Selected date is closed.");
//        }
//
//        // 3. Check slot capacity
//        TimeSlot slot = timeSlotRepository.findById(dto.getTimeSlotId())
//                .orElseThrow(() -> new InvalidTimeSlotException("Time slot not found"));
//
//        int currentBookings = ticketRepository.countByTimeSlotAndVisitDateAndTicketStatus(
//                slot, dto.getVisitDate(), TicketStatus.VALID);
//
//        if (currentBookings + dto.getQuantity() > slot.getMaxTickets()) {
//            throw new TimeSlotFullException("Not enough capacity for the selected time slot.");
//        }
//
//        // 4. Get matching price config
//        PriceConfig priceConfig = priceConfigRepository
//                .findByTicketTypeAndStudentType(ticketType, dto.getStudentType())
//                .orElseThrow(() -> new PriceConfigDoesNotFoundException("Price config not found for this student type."));
//
//        BigDecimal totalPrice = BigDecimal.valueOf(priceConfig.getPrice())
//                .multiply(BigDecimal.valueOf(dto.getQuantity()));
//
//
//        // 5. Create Tickets (or a GroupTicket entity)
//        List<Ticket> tickets = new ArrayList<>();
//
//        for (int i = 0; i < dto.getQuantity(); i++) {
//            Ticket ticket = Ticket.builder()
//                    .ticketType(ticketType)
//                    .visitDate(dto.getVisitDate())
//                    .timeSlot(slot)
//                    .ticketStatus(TicketStatus.PENDING)
//                    .issuedAt(LocalDateTime.now())
//                    .expiresAt(LocalDateTime.now().plusDays(ticketType.getTicketPolicy().getValidityDays()))
//                    .build();
//
//            tickets.add(ticket);
//        }
//
//        List<Ticket> savedTickets = ticketRepository.saveAll(tickets);
//
//        // 6. Return response
//        return TicketGroupBookingResponse.builder()
//                .ticketTypeName(ticketType.getName())
//                .studentType(dto.getStudentType())
//                .totalTickets(dto.getQuantity())
//                .totalPrice(totalPrice)
//                .tickets(savedTickets.stream().map(TicketMapper::MapTicketEntityToTicketDto).toList())
//                .build();
//    }
//
//}
