package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class InvalidTicketTypeException extends BaseApplicationException {
    public InvalidTicketTypeException(String message) {
        super(message);
    }
}
