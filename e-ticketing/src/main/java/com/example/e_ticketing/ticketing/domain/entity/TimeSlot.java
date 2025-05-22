package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "timeslots")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlot {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    private LocalTime startTime;
    private LocalTime endTime;

    private Boolean isActive;

    @OneToMany(mappedBy = "timeSlot")
    private List<Ticket> tickets;

    private int maxTickets;

    @OneToMany(mappedBy = "timeSlot")
    private List<QueueEntry> queueEntries;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
