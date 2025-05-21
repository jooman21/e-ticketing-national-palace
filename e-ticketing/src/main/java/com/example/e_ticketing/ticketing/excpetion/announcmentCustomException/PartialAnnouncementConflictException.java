package com.example.e_ticketing.ticketing.excpetion.announcmentCustomException;

import java.time.LocalDate;
import java.util.List;

public class PartialAnnouncementConflictException extends RuntimeException {
    private final List<LocalDate> conflictedDates;

    public PartialAnnouncementConflictException(List<LocalDate> conflictedDates) {
        super("Some dates have conflicts");
        this.conflictedDates = conflictedDates;
    }

    public List<LocalDate> getConflictedDates() {
        return conflictedDates;
    }
}

