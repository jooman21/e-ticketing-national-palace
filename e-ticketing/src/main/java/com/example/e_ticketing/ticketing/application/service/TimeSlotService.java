package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.TimeslotDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TimeSlotService {

    @Transactional
    List<TimeslotDto> createDailyTimeSlots(Integer maxTickets);
}
