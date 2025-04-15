package com.example.e_ticketing.ticketing.domain.entity;

import com.example.e_ticketing.ticketing.domain.valueobject.Residency;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class priceConfig {
    @Id
    @GeneratedValue
    private Long id;

    private Long ticketTypeId;

    @Enumerated(EnumType.STRING)
    private Residency residency;
    private String currency;
    private Double price;

    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
