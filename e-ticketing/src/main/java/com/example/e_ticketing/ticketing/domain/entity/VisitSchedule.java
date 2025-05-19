package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "visits_schedules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitSchedule {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    private LocalDate date;

    private Boolean isOpen = true;

    private String reasonForClosing;
    @OneToMany(mappedBy = "visitSchedule")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "visitSchedule")
    private List<QueueEntry> queueEntries;

    @ManyToMany
    @JoinTable(
            name = "visit_schedule_places",
            joinColumns = @JoinColumn(name = "visit_schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "visit_place_id")
    )
    @Builder.Default
    private List<VisitPlace> visitPlaces = new ArrayList<>();


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
