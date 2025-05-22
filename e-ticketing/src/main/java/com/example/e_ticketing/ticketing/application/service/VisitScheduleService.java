package com.example.e_ticketing.ticketing.application.service;



import java.time.LocalDate;

import java.util.List;


public interface VisitScheduleService  {
    List<LocalDate> getAllGateClosedDates();


}
