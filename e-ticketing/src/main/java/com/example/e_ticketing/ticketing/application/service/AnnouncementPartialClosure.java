package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.AnnouncementDto;
import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AnnouncementPartialClosure {



    List<AnnouncementDto> getPartialAvailabilityAnnouncements(LocalDate date, UUID visitPlaceId);

    @Transactional
    List<Announcement> createPartialAvailabilityAnnouncement(AnnouncementDto dto);
}
