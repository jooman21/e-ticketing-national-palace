package com.example.e_ticketing.ticketing.domain.entity;

import com.example.e_ticketing.ticketing.domain.valueobject.TicketStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;

    private Long visitorId;
    private Long ticketTypeId;
    //private Long eventId;
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;
    private String qrCode;

    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
}
