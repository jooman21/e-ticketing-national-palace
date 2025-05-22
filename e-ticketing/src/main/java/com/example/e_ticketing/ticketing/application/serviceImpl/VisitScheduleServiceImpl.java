package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.repository.VisitScheduleRepository;
import com.example.e_ticketing.ticketing.application.service.VisitScheduleService;
import com.example.e_ticketing.ticketing.excpetion.NoMuseumClosedDateFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
public class VisitScheduleServiceImpl implements VisitScheduleService {

    private final VisitScheduleRepository visitScheduleRepository;

    @Override
    public List<LocalDate> getAllGateClosedDates() {
        List<LocalDate> closedDates = visitScheduleRepository.findAllClosedDates();

        if (closedDates.isEmpty()) {
            throw new NoMuseumClosedDateFoundException("No Museum  closed dates found.");
        }

        return closedDates;
    }

}
