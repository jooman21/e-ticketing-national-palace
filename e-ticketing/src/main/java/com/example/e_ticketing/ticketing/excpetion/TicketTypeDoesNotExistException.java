package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class TicketTypeDoesNotExistException extends BaseApplicationException {
    public TicketTypeDoesNotExistException(String message) {
        super(message);
    }
}
