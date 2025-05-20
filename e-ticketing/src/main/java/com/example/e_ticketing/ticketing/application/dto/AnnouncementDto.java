package com.example.e_ticketing.ticketing.application.dto;

import com.example.e_ticketing.ticketing.domain.valueobject.AnnouncementType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementDto {
    private UUID id;
    private String subject;
    private String message;
    private AnnouncementType announcementType;
    private LocalDateTime effectiveDate;
    private List<UUID> visitPlaceIds;
}
