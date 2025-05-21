package com.example.e_ticketing.ticketing.application.mapper;

import com.example.e_ticketing.ticketing.application.dto.AnnouncementDto;
import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnnouncementMapper {

    // DTO → Entity using pre-fetched VisitPlace list
    public static Announcement toEntity(AnnouncementDto dto, List<VisitPlace> places) {
        Announcement announcement = new Announcement();
        announcement.setId(dto.getId());
        announcement.setSubject(dto.getSubject());
        announcement.setMessage(dto.getMessage());
        announcement.setAnnouncementType(dto.getAnnouncementType());

        // Just use the first date as representative in entity (entity still supports one date)
        if (dto.getEffectiveDates() != null && !dto.getEffectiveDates().isEmpty()) {
            announcement.setEffectiveDate(dto.getEffectiveDates().get(0));
        }

        announcement.setVisitPlaces(places != null ? places : new ArrayList<>());
        return announcement;
    }

    // DTO → Entity with automatic resolution of visitPlaces using a repository
    public static Announcement toEntity(AnnouncementDto dto, Function<List<UUID>, List<VisitPlace>> placeResolver) {
        List<VisitPlace> places = dto.getVisitPlaceIds() != null
                ? placeResolver.apply(dto.getVisitPlaceIds())
                : new ArrayList<>();
        return toEntity(dto, places);
    }

    // Entity → DTO
    public static AnnouncementDto toDto(Announcement announcement) {
        AnnouncementDto dto = new AnnouncementDto();
        dto.setId(announcement.getId());
        dto.setSubject(announcement.getSubject());
        dto.setMessage(announcement.getMessage());
        dto.setAnnouncementType(announcement.getAnnouncementType());

        // Wrap single effectiveDate into a list
        if (announcement.getEffectiveDate() != null) {
            dto.setEffectiveDates(List.of(announcement.getEffectiveDate()));
        }

        if (announcement.getVisitPlaces() != null) {
            dto.setVisitPlaceIds(
                    announcement.getVisitPlaces().stream()
                            .map(VisitPlace::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}
