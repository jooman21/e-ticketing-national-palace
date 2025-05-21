package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.AnnouncementDto;
import com.example.e_ticketing.ticketing.application.mapper.AnnouncementMapper;
import com.example.e_ticketing.ticketing.application.repository.AnnouncementRepository;
import com.example.e_ticketing.ticketing.application.repository.VisitPlaceRepository;
import com.example.e_ticketing.ticketing.application.repository.VisitSchedulePlaceStatusRepository;
import com.example.e_ticketing.ticketing.application.repository.VisitScheduleRepository;
import com.example.e_ticketing.ticketing.application.service.AnnouncementPartialClosure;
import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;
import com.example.e_ticketing.ticketing.domain.entity.VisitSchedule;
import com.example.e_ticketing.ticketing.domain.entity.VisitSchedulePlaceStatus;
import com.example.e_ticketing.ticketing.domain.valueobject.AnnouncementType;
import com.example.e_ticketing.ticketing.excpetion.ClosureDateAlreadyAssignedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartialAvailabilityAnnouncementServiceImpl implements AnnouncementPartialClosure {

    private final AnnouncementRepository announcementRepository;
    private final VisitPlaceRepository visitPlaceRepository;
    private final VisitScheduleRepository visitScheduleRepository;
    private final VisitSchedulePlaceStatusRepository visitSchedulePlaceStatusRepository;

    @Override
    @Transactional
    public List<Announcement> createPartialAvailabilityAnnouncement(AnnouncementDto dto) {
        List<Announcement> createdAnnouncements = new ArrayList<>();
        List<LocalDate> conflictingDates = new ArrayList<>();

        for (LocalDateTime effectiveDate : dto.getEffectiveDates()) {
            LocalDate closureDate = effectiveDate.toLocalDate();

            if (dto.getAnnouncementType() == AnnouncementType.PARTIAL_AVAILABILITY) {
                Optional<VisitSchedule> optionalSchedule = visitScheduleRepository.findByDate(closureDate);
                if (optionalSchedule.isPresent() && !optionalSchedule.get().getIsOpen()) {
                    conflictingDates.add(closureDate);
                    continue; // Skip processing this date
                }
            }
        }

        if (!conflictingDates.isEmpty()) {
            String dates = conflictingDates.stream()
                    .map(LocalDate::toString)
                    .collect(Collectors.joining(", "));
            throw new ClosureDateAlreadyAssignedException(
                    String.format("Closure already exists for the following dates: %s", dates)
            );
        }

        // Proceed with processing valid dates
        for (LocalDateTime effectiveDate : dto.getEffectiveDates()) {
            Announcement announcement = AnnouncementMapper.toEntity(dto, visitPlaceRepository::findAllById);
            announcement.setEffectiveDate(effectiveDate);
            announcement.setCreatedAt(LocalDateTime.now());
            announcement.setUpdatedAt(LocalDateTime.now());

            if (dto.getAnnouncementType() == AnnouncementType.PARTIAL_AVAILABILITY) {
                LocalDate closureDate = effectiveDate.toLocalDate();

                VisitSchedule schedule = visitScheduleRepository.findByDate(closureDate)
                        .orElseGet(() -> VisitSchedule.builder()
                                .date(closureDate)
                                .isOpen(false)
                                .reasonForClosing("Closed due to announcement: " + dto.getSubject())
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build());

                if (schedule.getId() == null) {
                    visitScheduleRepository.save(schedule);
                } else {
                    schedule.setIsOpen(false);
                    schedule.setReasonForClosing("Closed due to announcement: " + dto.getSubject());
                    schedule.setUpdatedAt(LocalDateTime.now());
                }

                if (schedule.getPlaceStatuses() == null) {
                    schedule.setPlaceStatuses(new ArrayList<>());
                }

                for (VisitPlace place : announcement.getVisitPlaces()) {
                    boolean alreadyExists = schedule.getPlaceStatuses().stream()
                            .anyMatch(status -> status.getVisitPlace().getId().equals(place.getId()));

                    if (!alreadyExists) {
                        VisitSchedulePlaceStatus status = VisitSchedulePlaceStatus.builder()
                                .visitSchedule(schedule)
                                .visitPlace(place)
                                .isOpen(false)
                                .reasonForClosing("Closed due to announcement: " + dto.getSubject())
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();
                        visitSchedulePlaceStatusRepository.save(status);
                    }
                }

                announcement.setVisitSchedule(schedule);
            }

            Announcement saved = announcementRepository.save(announcement);
            createdAnnouncements.add(saved);
        }

        return createdAnnouncements;
    }


}
