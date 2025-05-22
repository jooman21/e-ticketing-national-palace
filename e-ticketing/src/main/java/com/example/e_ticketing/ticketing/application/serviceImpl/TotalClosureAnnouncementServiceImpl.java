package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.AnnouncementDto;
import com.example.e_ticketing.ticketing.application.mapper.AnnouncementMapper;
import com.example.e_ticketing.ticketing.application.repository.AnnouncementRepository;
import com.example.e_ticketing.ticketing.application.repository.VisitPlaceRepository;

import com.example.e_ticketing.ticketing.application.repository.VisitScheduleRepository;
import com.example.e_ticketing.ticketing.application.service.AnnouncementTotalClosure;
import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import com.example.e_ticketing.ticketing.domain.entity.VisitSchedule;
import com.example.e_ticketing.ticketing.domain.valueobject.AnnouncementType;
import com.example.e_ticketing.ticketing.excpetion.AnnouncementNotFoundException;
import com.example.e_ticketing.ticketing.excpetion.AvailabilityConflictException;
import com.example.e_ticketing.ticketing.excpetion.ClosureDateAlreadyAssignedException;
import com.example.e_ticketing.ticketing.excpetion.InvalidVisitPlaceException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TotalClosureAnnouncementServiceImpl implements AnnouncementTotalClosure {

    private final AnnouncementRepository announcementRepository;
    private final VisitPlaceRepository visitPlaceRepository;
    private final VisitScheduleRepository visitScheduleRepository;


    @Override
    @Transactional
    public List<Announcement> create(AnnouncementDto dto) {
        List<Announcement> createdAnnouncements = new ArrayList<>();

        // 1. Validate TOTAL_CLOSURE must not include visitPlaceIds
        if (dto.getAnnouncementType() == AnnouncementType.TOTAL_CLOSURE &&
                dto.getVisitPlaceIds() != null && !dto.getVisitPlaceIds().isEmpty()) {
            throw new InvalidVisitPlaceException("Visit places must not be specified for TOTAL_CLOSURE announcements.");
        }

        List<LocalDate> conflictingDates = new ArrayList<>();

        // 2. Check for any conflicting closure or partial availability
        for (LocalDateTime effectiveDate : dto.getEffectiveDates()) {
            LocalDate closureDate = effectiveDate.toLocalDate();

            Optional<VisitSchedule> optionalSchedule = visitScheduleRepository.findByDate(closureDate);

            if (optionalSchedule.isPresent()) {
                VisitSchedule schedule = optionalSchedule.get();

                // If already fully closed, skip
                if (!schedule.getIsOpen()) {
                    conflictingDates.add(closureDate);
                    continue;
                }

                // If any VisitSchedulePlaceStatus exists => partial availability set
                if (schedule.getPlaceStatuses() != null && !schedule.getPlaceStatuses().isEmpty()) {
                    throw new AvailabilityConflictException(
                            String.format("Cannot apply TOTAL_CLOSURE on %s: partial availability already assigned.", closureDate)
                    );
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

        // 3. Process and create announcements
        for (LocalDateTime effectiveDate : dto.getEffectiveDates()) {
            Announcement announcement = AnnouncementMapper.toEntity(dto, visitPlaceRepository::findAllById);
            announcement.setEffectiveDate(effectiveDate);
            announcement.setCreatedAt(LocalDateTime.now());
            announcement.setUpdatedAt(LocalDateTime.now());

            if (dto.getAnnouncementType() == AnnouncementType.TOTAL_CLOSURE) {
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

                announcement.setVisitSchedule(schedule);
            }

            Announcement saved = announcementRepository.save(announcement);
            createdAnnouncements.add(saved);
        }

        return createdAnnouncements;
    }

    @Override
    public List<AnnouncementDto> getTotalClosureAnnouncements(LocalDate date) {
        List<Announcement> announcements;

        if (date != null) {
            announcements = announcementRepository.findByTypeAndDate(
                    AnnouncementType.TOTAL_CLOSURE, date);
        } else {
            announcements = announcementRepository.findByAnnouncementType(AnnouncementType.TOTAL_CLOSURE);
        }

        if (announcements.isEmpty()) {
            throw new AnnouncementNotFoundException("No Total closure announcements found for the given filters.");
        }

        return announcements.stream()
                .map(AnnouncementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDto> getAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAll();

        if (announcements.isEmpty()) {
            throw new AnnouncementNotFoundException("No announcements found.");
        }

        return announcements.stream()
                .map(AnnouncementMapper::toDto)
                .collect(Collectors.toList());
    }

}









