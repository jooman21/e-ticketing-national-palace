package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "queue_entries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueEntry {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

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
