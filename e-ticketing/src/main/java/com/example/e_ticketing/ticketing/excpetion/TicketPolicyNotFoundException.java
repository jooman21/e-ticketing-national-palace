package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class TicketPolicyNotFoundException extends BaseApplicationException {
    public TicketPolicyNotFoundException(String message) {
        super(message);
    }
}
