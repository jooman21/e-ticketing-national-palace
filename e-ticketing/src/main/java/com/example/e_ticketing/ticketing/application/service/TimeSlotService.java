package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.TimeSlotAvailabilityDto;
import com.example.e_ticketing.ticketing.application.dto.TimeslotDto;
import com.example.e_ticketing.ticketing.domain.entity.TimeSlot;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TimeSlotService {

    @Transactional
    List<TimeslotDto> createDailyTimeSlots(Integer maxTickets);

    // 🔽 Add this method here
    TimeSlot findNextAvailableTimeSlot(LocalDate date, TimeSlot currentSlot);

    List<TimeslotDto> getAllActiveTimeSlots();


    @Transactional
    TimeslotDto updateMaxTicketsForTimeSlot(UUID timeSlotId, Integer newMaxTickets);

    List<TimeSlotAvailabilityDto> getAvailableTimeSlotsForDay(LocalDate date);
}
