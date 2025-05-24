package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class InvalidBookingException extends BaseApplicationException {
    public InvalidBookingException(String message) {
        super(message);
    }
}
