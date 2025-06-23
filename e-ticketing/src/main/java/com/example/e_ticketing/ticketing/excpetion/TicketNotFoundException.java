package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class TicketNotFoundException extends BaseApplicationException {
    public TicketNotFoundException(String message) {
        super(message);
    }
}
