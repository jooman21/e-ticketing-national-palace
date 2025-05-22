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
import com.example.e_ticketing.ticketing.excpetion.AnnouncementNotFoundException;
import com.example.e_ticketing.ticketing.excpetion.ClosureDateAlreadyAssignedException;
import com.example.e_ticketing.ticketing.excpetion.DuplicatePartialAvailabilityException;
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
        Set<LocalDate> conflictingDates = new HashSet<>();

        for (LocalDateTime effectiveDate : dto.getEffectiveDates()) {
            LocalDate closureDate = effectiveDate.toLocalDate();

            if (dto.getAnnouncementType() == AnnouncementType.PARTIAL_AVAILABILITY) {
                // Check if the day is already fully closed
                Optional<VisitSchedule> optionalSchedule = visitScheduleRepository.findByDate(closureDate);
                if (optionalSchedule.isPresent() && !optionalSchedule.get().getIsOpen()) {
                    conflictingDates.add(closureDate);
                    continue;
                }

                // Check if partial availability already exists
                for (UUID placeId : dto.getVisitPlaceIds()) {
                    boolean exists = announcementRepository.existsByTypeAndEffectiveDateAndVisitPlaceId(
                            AnnouncementType.PARTIAL_AVAILABILITY,
                            effectiveDate,
                            placeId
                    );
                    if (exists) {
                        conflictingDates.add(closureDate);
                        break; // No need to check other places for this date
                    }
                }
            }
        }

        if (!conflictingDates.isEmpty()) {
            String dates = conflictingDates.stream()
                    .map(LocalDate::toString)
                    .sorted()
                    .collect(Collectors.joining(", "));
            throw new DuplicatePartialAvailabilityException(
                    String.format("Partial availability already exists for the following dates: %s", dates)
            );
        }

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
                                .isOpen(true)
                                .reasonForClosing("Visit Place Closed due to announcement: " + dto.getSubject())
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build());

                if (schedule.getId() == null) {
                    visitScheduleRepository.save(schedule);
                } else {
                    schedule.setIsOpen(true);
                    schedule.setReasonForClosing("Visit Place Closed due to announcement: " + dto.getSubject());
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
                                .isOpen(true)
                                .reasonForClosing("Visit Place Closed due to announcement: " + dto.getSubject())
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



    @Override
    public List<AnnouncementDto> getPartialAvailabilityAnnouncements(LocalDate date, UUID visitPlaceId) {
        List<Announcement> announcements;

        if (date != null && visitPlaceId != null) {
            announcements = announcementRepository.findByTypeAndDateAndVisitPlace(
                    AnnouncementType.PARTIAL_AVAILABILITY, date, visitPlaceId);
        } else if (date != null) {
            announcements = announcementRepository.findByTypeAndDate(
                    AnnouncementType.PARTIAL_AVAILABILITY, date);
        } else if (visitPlaceId != null) {
            announcements = announcementRepository.findByTypeAndVisitPlace(
                    AnnouncementType.PARTIAL_AVAILABILITY, visitPlaceId);
        } else {
            announcements = announcementRepository.findByAnnouncementType(AnnouncementType.PARTIAL_AVAILABILITY);
        }

        if (announcements.isEmpty()) {
            throw new AnnouncementNotFoundException("No partial availability announcements found for the given filters.");
        }

        return announcements.stream()
                .map(AnnouncementMapper::toDto)
                .collect(Collectors.toList());
    }


}
