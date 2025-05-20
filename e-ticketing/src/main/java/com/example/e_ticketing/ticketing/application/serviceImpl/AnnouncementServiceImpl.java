package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.AnnouncementDto;
import com.example.e_ticketing.ticketing.application.mapper.AnnouncementMapper;
import com.example.e_ticketing.ticketing.application.repository.AnnouncementRepository;
import com.example.e_ticketing.ticketing.application.repository.VisitPlaceRepository;
import com.example.e_ticketing.ticketing.application.repository.VisitScheduleRepository;
import com.example.e_ticketing.ticketing.application.service.AnnouncementService;
import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;
import com.example.e_ticketing.ticketing.domain.entity.VisitSchedule;
import com.example.e_ticketing.ticketing.domain.entity.VisitSchedulePlaceStatus;
import com.example.e_ticketing.ticketing.domain.valueobject.AnnouncementType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final VisitPlaceRepository visitPlaceRepository;
    private final VisitScheduleRepository visitScheduleRepository;

    @Override
    @Transactional
    public Announcement createAnnouncement(AnnouncementDto dto) {
        List<VisitPlace> places = visitPlaceRepository.findAllById(dto.getVisitPlaceIds());
        Announcement announcement = AnnouncementMapper.toEntity(dto, places);

        // Handle museum closure
        if (dto.getAnnouncementType() == AnnouncementType.CLOSURE) {
            LocalDate date = dto.getEffectiveDate().toLocalDate();

            // Create or fetch schedule for the closure date
            VisitSchedule schedule = visitScheduleRepository.findByDate(date)
                    .orElseGet(() -> {
                        VisitSchedule newSchedule = new VisitSchedule();
                        newSchedule.setDate(date);
                        newSchedule.setPlaceStatuses(new ArrayList<>());
                        return newSchedule;
                    });

            schedule.setIsOpen(false); // Mark the museum as closed
            schedule.setReasonForClosing("Closed due to announcement: " + dto.getSubject());

            // For each place, mark as unavailable in place statuses
            for (VisitPlace place : places) {
                boolean exists = schedule.getPlaceStatuses().stream()
                        .anyMatch(ps -> ps.getVisitPlace().getId().equals(place.getId()));

                if (!exists) {
                    VisitSchedulePlaceStatus status = VisitSchedulePlaceStatus.builder()
                            .visitPlace(place)
                            .visitSchedule(schedule)
                           // .status("UNAVAILABLE")
                            .isOpen(false)
                            .reasonForClosing("Closed due to announcement")
                            .build();
                    schedule.getPlaceStatuses().add(status);
                }
            }

            visitScheduleRepository.save(schedule);

            // Link the schedule to the announcement
            announcement.setVisitSchedule(schedule);
        }

        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setUpdatedAt(LocalDateTime.now());

        return announcementRepository.save(announcement);
    }
}
