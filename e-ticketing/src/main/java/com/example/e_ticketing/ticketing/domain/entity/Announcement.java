package com.example.e_ticketing.ticketing.domain.entity;

import com.example.e_ticketing.ticketing.domain.valueobject.AnnouncementType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
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

    @ManyToMany
    @JoinTable(
            name = "announcement_places",
            joinColumns = @JoinColumn(name = "announcement_id"),
            inverseJoinColumns = @JoinColumn(name = "visit_place_id")
    )
    @Builder.Default
    private List<VisitPlace> visitPlaces = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private AnnouncementType type;

    @ManyToOne
    @JoinColumn(name = "visit_schedule_id")
    private VisitSchedule visitSchedule; // optional - applies to a specific date


    private LocalDateTime effectiveDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
