package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.UnavailableDateDto;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public interface VisitScheduleService  {


    void markDateAsUnavailable(UUID visitPlaceId, LocalDate date, String reason);

    void markDateRangeAsUnavailable(UUID visitPlaceId, LocalDate start, LocalDate end, String reason);

    void markMultipleDatesAsUnavailable(UUID visitPlaceId, List<LocalDate> dates, String reason);

    List<LocalDate> getClosedDatesByVisitPlace(UUID visitPlaceId);

//    List<UnavailableDateDto> getUnavailableDates(UUID visitPlaceId, YearMonth month);
}
