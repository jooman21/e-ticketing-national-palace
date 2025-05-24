package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class ActiveTimeSlotNotFoundException extends BaseApplicationException {
    public ActiveTimeSlotNotFoundException(String message) {
        super(message);
    }
}
