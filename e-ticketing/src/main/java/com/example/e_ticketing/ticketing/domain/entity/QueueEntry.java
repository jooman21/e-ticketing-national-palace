package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class QueueEntry {
    @Id
    @GeneratedValue
    private Long id;

    private Long visitorId;
    private Long eventId;
    private Integer queuePosition;
}
