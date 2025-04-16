package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class TicketType {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    private Double basePrice;
    private Boolean available;

    @OneToMany(mappedBy = "ticketType")
    private List<PriceConfig> priceConfigs;

    @OneToMany(mappedBy = "ticketType")
    private List<Ticket> tickets;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
