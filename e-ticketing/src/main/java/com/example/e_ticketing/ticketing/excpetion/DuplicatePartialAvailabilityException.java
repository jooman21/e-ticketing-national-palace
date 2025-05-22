package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class DuplicatePartialAvailabilityException  extends BaseApplicationException {
    public DuplicatePartialAvailabilityException(String message) {
        super(message);
    }
}
