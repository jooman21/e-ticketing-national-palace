package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "visit_schedule_place_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitSchedulePlaceStatus {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "visit_schedule_id", nullable = false)
    private VisitSchedule visitSchedule;

    @ManyToOne
    @JoinColumn(name = "visit_place_id", nullable = false)
    private VisitPlace visitPlace;

    private Boolean isOpen = true;

    private String reasonForClosing;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
