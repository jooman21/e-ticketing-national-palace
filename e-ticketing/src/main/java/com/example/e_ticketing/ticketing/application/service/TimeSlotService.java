package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.TimeSlotAvailabilityDto;
import com.example.e_ticketing.ticketing.application.dto.TimeslotDto;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotService {

    @Transactional
    List<TimeslotDto> createDailyTimeSlots(Integer maxTickets);

    List<TimeslotDto> getAllActiveTimeSlots();

    List<TimeSlotAvailabilityDto> getAvailableTimeSlotsForDay(LocalDate date);
}
