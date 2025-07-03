package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.TimeSlotAvailabilityDto;
import com.example.e_ticketing.ticketing.application.dto.TimeslotDto;
import com.example.e_ticketing.ticketing.application.mapper.TimeSlotMapper;
import com.example.e_ticketing.ticketing.application.repository.QueueEntryRepository;
import com.example.e_ticketing.ticketing.application.repository.TicketRepository;
import com.example.e_ticketing.ticketing.application.repository.TimeSlotRepository;
import com.example.e_ticketing.ticketing.application.service.TimeSlotService;
import com.example.e_ticketing.ticketing.domain.entity.TimeSlot;
import com.example.e_ticketing.ticketing.domain.valueobject.TicketStatus;
import com.example.e_ticketing.ticketing.excpetion.ActiveTimeSlotNotFoundException;
import com.example.e_ticketing.ticketing.excpetion.InvalidDateException;
import com.example.e_ticketing.ticketing.excpetion.InvalidTimeSlotException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotMapper timeSlotMapper;
    private final QueueEntryRepository queueEntryRepository;
    private final TicketRepository ticketRepository;


        private static final LocalTime OPEN_TIME = LocalTime.of(9, 0);   // 9:00 AM
        private static final LocalTime CLOSE_TIME = LocalTime.of(17, 0); // 5:00 PM


    @Override
    @Transactional
    public List<TimeslotDto> createDailyTimeSlots(Integer maxTickets) {
        if (maxTickets == null || maxTickets <= 0) {
            throw new InvalidTimeSlotException("Max tickets must be greater than 0");
        }

        List<TimeslotDto> result = new ArrayList<>();

        LocalTime current = OPEN_TIME;
        while (current.plusHours(1).compareTo(CLOSE_TIME) <= 0) {
            LocalTime end = current.plusHours(1);

            // ✅ Check if a time slot with same time and maxTickets already exists
            boolean alreadyExists = timeSlotRepository.existsByStartTimeAndEndTimeAndMaxTicketsAndIsActiveTrue(current, end, maxTickets);
            if (alreadyExists) {
                throw new InvalidTimeSlotException("Time slot from " + current + " to " + end + " with max tickets " + maxTickets + " already exists.");
            }

            TimeSlot slot = new TimeSlot();
            slot.setStartTime(current);
            slot.setEndTime(end);
            slot.setIsActive(true);
            slot.setMaxTickets(maxTickets);
            slot.setCreatedAt(LocalDateTime.now());
            slot.setUpdatedAt(LocalDateTime.now());

            TimeSlot saved = timeSlotRepository.save(slot);
            result.add(timeSlotMapper.MapTimeSlotEntityToTimeSlotDto(saved));

            current = end;
        }

        return result;
    }


    // 🔽 Add this method here
    @Override
    public TimeSlot findNextAvailableTimeSlot(LocalDate date, TimeSlot currentSlot) {
        List<TimeSlot> slots = timeSlotRepository.findAllByIsActiveTrueOrderByStartTime();

        boolean passedCurrent = false;
        for (TimeSlot slot : slots) {
            if (slot.getStartTime().equals(currentSlot.getStartTime())) {
                passedCurrent = true;
                continue;
            }
            if (passedCurrent) {
                int count = ticketRepository.countByTimeSlotAndVisitDateAndTicketStatus(slot, date, TicketStatus.PENDING);
                int queueCount = queueEntryRepository.countByTimeSlotAndVisitSchedule_Date(slot, date);
                if (count + queueCount < slot.getMaxTickets()) {
                    return slot;
                }
            }
        }

        // Try next day
        return findNextAvailableTimeSlot(date.plusDays(1), new TimeSlot()); // optionally handle base case to avoid infinite recursion
    }

    @Override
    public List<TimeslotDto> getAllActiveTimeSlots() {
        return timeSlotRepository.findByIsActiveTrueOrderByStartTimeAsc()
                .stream()
                .map(timeSlotMapper::MapTimeSlotEntityToTimeSlotDto)  // instance method ref
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public TimeslotDto updateMaxTicketsForTimeSlot(UUID timeSlotId, Integer newMaxTickets) {
        if (newMaxTickets == null || newMaxTickets <= 0) {
            throw new InvalidTimeSlotException("Max tickets must be greater than 0");
        }

        // ✅ Correct usage of timeSlotId
        TimeSlot slot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new InvalidTimeSlotException("Time slot not found with ID: " + timeSlotId));

        // Update the maxTickets
        slot.setMaxTickets(newMaxTickets);
        slot.setUpdatedAt(LocalDateTime.now());

        // Save changes
        TimeSlot updated = timeSlotRepository.save(slot);

        return timeSlotMapper.MapTimeSlotEntityToTimeSlotDto(updated);
    }





    /**
     * Retrieves a list of available time slots for a specific date along with their availability status.
     *
     * This method performs the following steps:
     * 1. Validates that the provided date is not null; otherwise, throws an InvalidDateException.
     * 2. Fetches all active time slots from the repository. If no active time slots are found,
     *    throws an ActiveTimeSlotNotFoundException.
     * 3. For each active time slot:
     *    - Counts the number of tickets already sold for that time slot on the given date.
     *    - Counts the number of queue entries (e.g., people on a waiting list) for the same time slot and date.
     *    - Calculates the total number of people (tickets + queue entries) using that time slot.
     *    - Constructs a TimeSlotAvailabilityDto with:
     *        - Time slot details (id, start time, end time, max tickets).
     *        - Current usage count (currentTickets).
     *        - Active status (from the entity).
     *        - Availability status (computed dynamically as true if total usage is less than max tickets).
     *
     * Note:
     * - `isAvailable` is **not stored** in the TimeSlot entity because it depends on runtime conditions
     *   (number of bookings on a specific date) and must be computed dynamically.
     * - `isActive` is a static attribute stored in the database, indicating if a time slot is enabled.
     *
     * @param date the date for which availability is to be checked
     * @return list of TimeSlotAvailabilityDto containing availability information for each time slot
     * @throws InvalidDateException if the input date is null
     * @throws ActiveTimeSlotNotFoundException if no active time slots are configured
     */




    @Override
    public List<TimeSlotAvailabilityDto> getAvailableTimeSlotsForDay(LocalDate date) {
        if (date == null) throw new InvalidDateException("Date must be provided");

        List<TimeSlot> timeSlots = timeSlotRepository.findAllByIsActiveTrue();

        if (timeSlots.isEmpty()) throw new ActiveTimeSlotNotFoundException("No active time slots found");

        return timeSlots.stream().map(slot -> {
            int ticketCount = ticketRepository.countByTimeSlotAndVisitDateAndTicketStatusIn(slot, date, List.of(TicketStatus.PENDING));
            int queueCount = queueEntryRepository.countByTimeSlotAndVisitSchedule_Date(slot, date);
            int total = ticketCount + queueCount;

            TimeSlotAvailabilityDto dto = new TimeSlotAvailabilityDto();
            dto.setId(slot.getId());
            dto.setStartTime(slot.getStartTime());
            dto.setEndTime(slot.getEndTime());
            dto.setMaxTickets(slot.getMaxTickets());
            dto.setCurrentTickets(total);
            dto.setRemainingTickets(Math.max(0, slot.getMaxTickets() - total));
            dto.setIsAvailable(total < slot.getMaxTickets());
            dto.setIsActive(slot.getIsActive());

            return dto;
        }).collect(Collectors.toList());
    }



}


