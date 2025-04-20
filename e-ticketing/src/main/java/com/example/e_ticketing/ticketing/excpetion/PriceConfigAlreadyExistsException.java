package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class PriceConfigAlreadyExistsException extends BaseApplicationException {
    public PriceConfigAlreadyExistsException(String message) {
        super(message);
    }
}
