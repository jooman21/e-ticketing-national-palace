package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "Visit_places")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VisitPlace {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name; // e.g., "Museum", "Gallery", "Garden"

    private Boolean isAvailable = true;

    @ManyToMany(mappedBy = "visitPlaces")
    private List<TicketType> ticketTypes;

    @OneToMany(mappedBy = "visitPlace")
    private List<VisitSchedulePlaceStatus> scheduleStatuses;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
