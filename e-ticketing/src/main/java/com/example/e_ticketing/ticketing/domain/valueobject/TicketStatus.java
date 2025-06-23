package com.example.e_ticketing.ticketing.domain.valueobject;

public enum TicketStatus {

    PENDING,   // Created but not yet paid
    VALID,     // Payment successful
    IN_VALID,  // Used or invalidated manually (e.g., scanned twice)
    EXPIRED    // Not used within validity window
}
