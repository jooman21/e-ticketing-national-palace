package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class TimeSlotFullException extends BaseApplicationException {
    public TimeSlotFullException(String message) {
        super(message);
    }
}
