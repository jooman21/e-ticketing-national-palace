package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class VisitPlaceDoesNotExistException extends BaseApplicationException {
    public VisitPlaceDoesNotExistException(String message) {
        super(message);
    }
}
