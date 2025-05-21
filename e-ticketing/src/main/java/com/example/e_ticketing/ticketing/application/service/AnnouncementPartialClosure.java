package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.AnnouncementDto;
import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import jakarta.transaction.Transactional;

import java.util.List;

public interface AnnouncementPartialClosure {
    @Transactional
    List<Announcement> createPartialAvailabilityAnnouncement(AnnouncementDto dto);

    //List<Announcement> create(AnnouncementDto dto);
}
