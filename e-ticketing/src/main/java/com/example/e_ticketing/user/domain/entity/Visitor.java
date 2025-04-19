package com.example.e_ticketing.user.domain.entity;

import com.example.e_ticketing.ticketing.domain.entity.Ticket;
import com.example.e_ticketing.ticketing.domain.valueobject.Residency;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "visitors")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Residency residency;

    private LocalDateTime registeredAt;

    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
