package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;



public class PriceConfigDoesNotFoundException extends BaseApplicationException {
    public PriceConfigDoesNotFoundException(String message) {
        super(message);
    }
}
