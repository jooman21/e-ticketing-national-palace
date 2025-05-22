package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.AnnouncementDto;
import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface AnnouncementTotalClosure {
    @Transactional
    List<Announcement> create(AnnouncementDto dto);

    List<AnnouncementDto> getTotalClosureAnnouncements(LocalDate date);
}
