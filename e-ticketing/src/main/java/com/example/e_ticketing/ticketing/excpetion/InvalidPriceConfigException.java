package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class InvalidPriceConfigException extends BaseApplicationException {
    public InvalidPriceConfigException(String message) {
        super(message);
    }
}
