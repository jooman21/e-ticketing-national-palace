package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class InvalidTimeSlotException extends BaseApplicationException {
    public InvalidTimeSlotException(String message) {
        super(message);
    }
}
