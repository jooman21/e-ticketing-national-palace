package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class PriceConfigExistsException  extends BaseApplicationException {
    public PriceConfigExistsException(String message) {
        super(message);
    }
}
