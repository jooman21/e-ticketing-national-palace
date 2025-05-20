package com.example.e_ticketing.ticketing.application.mapper;

import com.example.e_ticketing.ticketing.application.dto.AnnouncementDto;
import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnnouncementMapper {

    public static AnnouncementDto toDto(Announcement announcement) {
        AnnouncementDto dto = new AnnouncementDto();
        dto.setId(announcement.getId());
        dto.setSubject(announcement.getSubject());
        dto.setMessage(announcement.getMessage());
        dto.setAnnouncementType(announcement.getType()); // FIXED this line
        dto.setEffectiveDate(announcement.getEffectiveDate());

        if (announcement.getVisitPlaces() != null) {
            dto.setVisitPlaceIds(
                    announcement.getVisitPlaces().stream()
                            .map(VisitPlace::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static Announcement toEntity(AnnouncementDto dto, List<VisitPlace> places) {
        Announcement announcement = new Announcement();
        announcement.setId(dto.getId());
        announcement.setSubject(dto.getSubject());
        announcement.setMessage(dto.getMessage());
        announcement.setType(dto.getAnnouncementType()); // FIXED this line
        announcement.setEffectiveDate(dto.getEffectiveDate());
        announcement.setVisitPlaces(places != null ? places : new ArrayList<>());
        return announcement;
    }
}

