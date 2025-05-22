package com.example.e_ticketing.ticketing.application.mapper;

import com.example.e_ticketing.ticketing.application.dto.TimeslotDto;
import com.example.e_ticketing.ticketing.domain.entity.TimeSlot;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class TimeSlotMapper {
    public TimeSlot MapTimeSlotDtoToEntity(TimeslotDto dto) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartTime(dto.getStartTime());
        timeSlot.setEndTime(dto.getEndTime());
        timeSlot.setIsActive(dto.getIsActive());
        timeSlot.setMaxTickets(dto.getMaxTickets());
        timeSlot.setCreatedAt(LocalDateTime.now());
        timeSlot.setUpdatedAt(LocalDateTime.now());
        return timeSlot;
    }
    public TimeslotDto MapTimeSlotEntityToTimeSlotDto(TimeSlot timeSlot) {
        TimeslotDto dto = new TimeslotDto();
        dto.setId(timeSlot.getId());
        dto.setStartTime(timeSlot.getStartTime());
        dto.setEndTime(timeSlot.getEndTime());
        dto.setIsActive(timeSlot.getIsActive());
        dto.setMaxTickets(timeSlot.getMaxTickets());
        dto.setCreatedAt(timeSlot.getCreatedAt());
        dto.setUpdatedAt(timeSlot.getUpdatedAt());
        return dto;
    }
}
