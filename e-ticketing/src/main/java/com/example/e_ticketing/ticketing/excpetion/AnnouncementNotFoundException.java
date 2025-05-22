package com.example.e_ticketing.ticketing.excpetion;

import com.example.e_ticketing.expection.BaseApplicationException;

public class AnnouncementNotFoundException extends BaseApplicationException {
    public AnnouncementNotFoundException(String message) {
        super(message);
    }
}
