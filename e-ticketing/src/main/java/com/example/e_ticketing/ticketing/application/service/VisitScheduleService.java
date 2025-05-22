package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.UnavailableDateDto;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public interface VisitScheduleService  {
    List<LocalDate> getAllGateClosedDates();


}
