package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Inventory {
    @Id
    @GeneratedValue
    private Long id;

    private Long eventId;

    private Integer total;
    private Integer available;
    private Integer reserved;
}
