package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.AnnouncementDto;
import com.example.e_ticketing.ticketing.application.mapper.AnnouncementMapper;
import com.example.e_ticketing.ticketing.application.repository.AnnouncementRepository;
import com.example.e_ticketing.ticketing.application.repository.VisitPlaceRepository;
import com.example.e_ticketing.ticketing.application.repository.VisitSchedulePlaceStatusRepository;
import com.example.e_ticketing.ticketing.application.repository.VisitScheduleRepository;
import com.example.e_ticketing.ticketing.application.service.AnnouncementService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final VisitPlaceRepository visitPlaceRepository;
    private final VisitScheduleRepository visitScheduleRepository;
    private final VisitSchedulePlaceStatusRepository visitSchedulePlaceStatusRepository;

    @Override
    @Transactional
    public List<Announcement> createAnnouncement(AnnouncementDto dto) {
        List<Announcement> createdAnnouncements = new ArrayList<>();

        for (LocalDateTime effectiveDate : dto.getEffectiveDates()) {
            Announcement announcement = AnnouncementMapper.toEntity(dto, visitPlaceRepository::findAllById);
            announcement.setEffectiveDate(effectiveDate);
            announcement.setCreatedAt(LocalDateTime.now());
            announcement.setUpdatedAt(LocalDateTime.now());

            if (dto.getAnnouncementType() == AnnouncementType.CLOSURE) {
                LocalDate closureDate = effectiveDate.toLocalDate();

                Optional<VisitSchedule> optionalSchedule = visitScheduleRepository.findByDate(closureDate);

                if (optionalSchedule.isPresent()) {
                    VisitSchedule existingSchedule = optionalSchedule.get();

                    // 🚫 If schedule is already closed, reject this closure announcement
                    if (!existingSchedule.getIsOpen()) {
                        throw new ClosureDateAlreadyAssignedException(
                                String.format("Closure already exists for date: %s", closureDate)
                        );
                    }
                }

                VisitSchedule schedule = optionalSchedule.orElseGet(() -> VisitSchedule.builder()
                        .date(closureDate)
                        .isOpen(false)
                        .reasonForClosing("Closed due to announcement: " + dto.getSubject())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
                );

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
