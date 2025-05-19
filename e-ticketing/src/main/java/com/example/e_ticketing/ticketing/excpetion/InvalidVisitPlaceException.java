package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class InvalidVisitPlaceException extends BaseApplicationException {
    public InvalidVisitPlaceException(String message) {
        super(message);
    }
}
