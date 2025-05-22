package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class NoMuseumClosedDateFoundException extends BaseApplicationException {
    public NoMuseumClosedDateFoundException(String message) {
        super(message);
    }
}
