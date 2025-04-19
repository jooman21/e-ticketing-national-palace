package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class TicketTypeAlreadyExistsException extends BaseApplicationException {
    public TicketTypeAlreadyExistsException(String message) {
        super(message);
    }
}
