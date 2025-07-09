package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class TicketCategoryNotFoundException extends BaseApplicationException {
    public TicketCategoryNotFoundException(String message) {
        super(message);
    }
}
