package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class VisitPlaceAlreadyExistsException extends BaseApplicationException {
    public VisitPlaceAlreadyExistsException(String message) {
        super(message);
    }
}
