package com.example.e_ticketing.ticketing.application.dto;

import com.example.e_ticketing.ticketing.domain.valueobject.AnnouncementType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformationalAnnouncementDto {
    private UUID id;
    private String subject;
    private String message;
    private AnnouncementType announcementType;
}
