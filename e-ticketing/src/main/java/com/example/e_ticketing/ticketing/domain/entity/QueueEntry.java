package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class QueueEntry {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "visit_schedule_id")
    private VisitSchedule visitSchedule;

    @ManyToOne
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot;
    //private Long visitorId;
    //private Long eventId;
    private Integer queuePosition;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
