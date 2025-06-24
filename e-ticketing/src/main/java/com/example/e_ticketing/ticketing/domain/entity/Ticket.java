package com.example.e_ticketing.ticketing.domain.entity;

import com.example.e_ticketing.ticketing.domain.valueobject.TicketStatus;
import com.example.e_ticketing.ticketing.domain.entity.Visitor;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id", nullable = true) // ✅ allow null in DB
    private Visitor visitor;


    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

//    @ManyToOne
//    @JoinColumn(name = "visit_schedule_id")
//    private VisitSchedule visitSchedule;

    @Column(nullable = false)
    private LocalDate visitDate;

    @ManyToOne
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot;
    //private Long eventId;
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;


    private String qrCode;

    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;


}
