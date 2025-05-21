package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class ClosureDateAlreadyAssignedException extends BaseApplicationException {
    public ClosureDateAlreadyAssignedException(String message) {
        super(message);
    }
}
