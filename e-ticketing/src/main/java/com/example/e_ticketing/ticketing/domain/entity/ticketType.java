package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
@Entity
public class ticketType {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    private Double basePrice;
    private Boolean available;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
