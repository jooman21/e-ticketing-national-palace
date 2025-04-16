package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "queue_entries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
