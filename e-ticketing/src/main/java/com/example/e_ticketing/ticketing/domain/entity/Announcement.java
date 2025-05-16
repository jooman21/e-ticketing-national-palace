package com.example.e_ticketing.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "announcement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announcement {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;
    private String subject;
    private String message;

    @ElementCollection
    @CollectionTable(name = "announcement_target_places", joinColumns = @JoinColumn(name = "announcement_id"))
    @Column(name = "place")
    private List<String> targetPlaces = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
