package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "visits_schedules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
