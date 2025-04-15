package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class timeSlot {
    @Id
    @GeneratedValue
    private Long id;

    private LocalTime startTime;
    private LocalTime endTime;

    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
