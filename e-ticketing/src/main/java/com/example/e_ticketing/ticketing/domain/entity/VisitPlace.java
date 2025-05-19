package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Visit_places")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"visitSchedules", "ticketTypes"})
@ToString(exclude = {"visitSchedules", "ticketTypes"})
public class VisitPlace {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name; // e.g., "Museum", "Gallery", "Garden"

    @ManyToMany(mappedBy = "visitPlaces")
    private List<VisitSchedule> visitSchedules;

    @ManyToMany(mappedBy = "visitPlaces")
    private List<TicketType> ticketTypes;
}
