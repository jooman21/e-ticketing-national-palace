package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.AnnouncementDto;
import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import jakarta.transaction.Transactional;

public interface AnnouncementService {
    @Transactional
    Announcement createAnnouncement(AnnouncementDto dto);
}
