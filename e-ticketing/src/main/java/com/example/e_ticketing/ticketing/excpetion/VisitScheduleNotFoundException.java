package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class VisitScheduleNotFoundException extends BaseApplicationException {
    public VisitScheduleNotFoundException(String message) {
        super(message);
    }
}
