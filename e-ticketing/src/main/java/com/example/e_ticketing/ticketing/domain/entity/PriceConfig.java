package com.example.e_ticketing.ticketing.domain.entity;

import com.example.e_ticketing.ticketing.domain.valueobject.Currency;
import com.example.e_ticketing.ticketing.domain.valueobject.Residency;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "price_configs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceConfig {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    @Enumerated(EnumType.STRING)
    private Residency residency;


    private String visitorType;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Double price;

    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
