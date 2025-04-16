package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "timeslots")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
