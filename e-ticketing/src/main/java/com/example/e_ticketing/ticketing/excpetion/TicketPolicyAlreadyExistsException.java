package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class TicketPolicyAlreadyExistsException extends BaseApplicationException {
    public TicketPolicyAlreadyExistsException(String message) {
        super(message);
    }
}
