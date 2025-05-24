package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ticket_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketType {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String name;
    private String description;

    @Builder.Default
    private Boolean available = true;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Builder.Default
    private Boolean isRecommended = false;

    @ManyToMany
    @JoinTable(
            name = "ticket_type_visit_place",
            joinColumns = @JoinColumn(name = "ticket_type_id"),
            inverseJoinColumns = @JoinColumn(name = "visit_place_id")
    )
    @Builder.Default
    private List<VisitPlace> visitPlaces = new ArrayList<>();

    @ManyToOne
    private TicketPolicy ticketPolicy;

    @OneToMany(mappedBy = "ticketType")
    private List<PriceConfig> priceConfigs;

    @OneToMany(mappedBy = "ticketType")
    private List<Ticket> tickets;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
