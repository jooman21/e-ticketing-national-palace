package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class InvalidDateException extends BaseApplicationException {
    public InvalidDateException(String message) {
        super(message);
    }
}
