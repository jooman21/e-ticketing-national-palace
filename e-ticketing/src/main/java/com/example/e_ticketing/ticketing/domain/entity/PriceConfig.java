package com.example.e_ticketing.ticketing.domain.entity;

import com.example.e_ticketing.ticketing.domain.valueobject.Currency;
import com.example.e_ticketing.ticketing.domain.valueobject.Residency;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "price_configs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    @Enumerated(EnumType.STRING)
    private Residency residency;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Double price;

    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
