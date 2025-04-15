package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
public class VisitSchedule {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;
    private Boolean isOpen;

    private String reasonForClosing;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
