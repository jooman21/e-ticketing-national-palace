package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.TimeslotDto;
import com.example.e_ticketing.ticketing.application.mapper.TimeSlotMapper;
import com.example.e_ticketing.ticketing.application.repository.TimeSlotRepository;
import com.example.e_ticketing.ticketing.application.service.TimeSlotService;
import com.example.e_ticketing.ticketing.domain.entity.TimeSlot;
import com.example.e_ticketing.ticketing.excpetion.InvalidTimeSlotException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotMapper timeSlotMapper;


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
                TimeSlot slot = new TimeSlot();
                slot.setStartTime(current);
                slot.setEndTime(current.plusHours(1));
                slot.setIsActive(true);
                slot.setMaxTickets(maxTickets);
                slot.setCreatedAt(LocalDateTime.now());
                slot.setUpdatedAt(LocalDateTime.now());

                TimeSlot saved = timeSlotRepository.save(slot);
                result.add(timeSlotMapper.MapTimeSlotEntityToTimeSlotDto(saved));
                current = current.plusHours(1);
            }

            return result;
        }
}


