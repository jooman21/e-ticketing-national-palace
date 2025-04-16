package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class PriceConfigNotFoundException extends BaseApplicationException {
    public PriceConfigNotFoundException(String message) {
        super(message);
    }
}
