package com.example.e_ticketing.ticketing.domain.entity;

import com.example.e_ticketing.ticketing.domain.valueobject.Residency;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "visitors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Visitor {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String fullName;
    private String email;
    private String phoneNumber;
    private String nationality;

    @Enumerated(EnumType.STRING)
    private Residency residency;

    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
