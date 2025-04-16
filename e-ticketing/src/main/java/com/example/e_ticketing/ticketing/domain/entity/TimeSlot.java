package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
public class TimeSlot {
    @Id
    @GeneratedValue
    private Long id;

    private LocalTime startTime;
    private LocalTime endTime;

    private Boolean isActive;

    @OneToMany(mappedBy = "timeSlot")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "timeSlot")
    private List<QueueEntry> queueEntries;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
