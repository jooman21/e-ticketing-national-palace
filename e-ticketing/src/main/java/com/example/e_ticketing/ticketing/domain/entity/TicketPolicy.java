package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ticket-policy")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketPolicy {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;
    private Integer validityDays;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
