package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class VisitSchedule {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;
    private Boolean isOpen;

    private String reasonForClosing;
    @OneToMany(mappedBy = "visitSchedule")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "visitSchedule")
    private List<QueueEntry> queueEntries;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
