package com.example.e_ticketing.event.domain.entity;

import com.example.e_ticketing.event.domain.valueobject.ProposalStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_proposals")
public class EventProposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long proposerId;

    private LocalDateTime proposedDate;

    private String agenda;

    private Integer attendeesCount;

    private String companyName;

    private String agencyName;

    private String eventType;

    private Boolean isPrivate;

    private Boolean isTicketed;

    @Enumerated(EnumType.STRING)
    private ProposalStatus status;

    @Column(columnDefinition = "TEXT")
    private String adminComment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
