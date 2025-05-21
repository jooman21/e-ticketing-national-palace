package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class AvailabilityConflictException extends BaseApplicationException {
    public AvailabilityConflictException(String message) {
        super(message);
    }
}
