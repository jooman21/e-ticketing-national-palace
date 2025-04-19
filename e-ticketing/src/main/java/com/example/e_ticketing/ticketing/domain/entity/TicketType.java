package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
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
    private Boolean available;

    @OneToMany(mappedBy = "ticketType")
    private List<PriceConfig> priceConfigs;

    @OneToMany(mappedBy = "ticketType")
    private List<Ticket> tickets;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
