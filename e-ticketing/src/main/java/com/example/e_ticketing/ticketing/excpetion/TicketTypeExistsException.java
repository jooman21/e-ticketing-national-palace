package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class TicketTypeExistsException extends BaseApplicationException {
    public TicketTypeExistsException(String message) {
        super(message);
    }
}
