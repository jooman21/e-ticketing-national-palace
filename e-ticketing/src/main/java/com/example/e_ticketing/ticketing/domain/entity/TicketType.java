package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
