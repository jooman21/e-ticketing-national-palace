package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.repository.VisitPlaceRepository;
import com.example.e_ticketing.ticketing.application.repository.VisitScheduleRepository;
import com.example.e_ticketing.ticketing.application.service.VisitScheduleService;
import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;
import com.example.e_ticketing.ticketing.domain.entity.VisitSchedule;
import com.example.e_ticketing.ticketing.domain.entity.VisitSchedulePlaceStatus;
import com.example.e_ticketing.ticketing.excpetion.VisitPlaceDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VisitScheduleServiceImpl implements VisitScheduleService {

    private final VisitScheduleRepository visitScheduleRepository;
//    private final AnnouncementRepository announcementRepository;
    private final VisitPlaceRepository visitPlaceRepository;

    @Override
    public void markDateAsUnavailable(UUID visitPlaceId, LocalDate date, String reason) {
        VisitSchedule schedule = visitScheduleRepository.findByDateAndVisitPlaceId(date, visitPlaceId)
                .orElseGet(() -> {
                    VisitSchedule newSchedule = new VisitSchedule();
                    newSchedule.setDate(date);
                    newSchedule.setIsOpen(true); // default state is open
                    newSchedule.setPlaceStatuses(new ArrayList<>());
                    return newSchedule;
                });

        VisitPlace visitPlace = (VisitPlace) visitPlaceRepository.findById(visitPlaceId)
                .orElseThrow(() -> new VisitPlaceDoesNotExistException("Visit place not found"));

        // Set the specific place as unavailable
        Optional<VisitSchedulePlaceStatus> optionalStatus = schedule.getPlaceStatuses().stream()
                .filter(ps -> ps.getVisitPlace().getId().equals(visitPlaceId))
                .findFirst();

        if (optionalStatus.isPresent()) {
            VisitSchedulePlaceStatus status = optionalStatus.get();
            status.setIsOpen(false);
            status.setReasonForClosing(reason);
        } else {
            VisitSchedulePlaceStatus placeStatus = VisitSchedulePlaceStatus.builder()
                    .visitPlace(visitPlace)
                    .visitSchedule(schedule)
                    .isOpen(false)
                    .reasonForClosing(reason)
                    .build();
            schedule.getPlaceStatuses().add(placeStatus);
        }

        // Optional: Close the entire museum if this place is the only one (or you have a separate global closure)

        visitScheduleRepository.save(schedule);
    }

    @Override
    public void markDateRangeAsUnavailable(UUID visitPlaceId, LocalDate start, LocalDate end, String reason) {
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            markDateAsUnavailable(visitPlaceId, date, reason); // reuse your existing single-day method
        }
    }
    @Override
    public void markMultipleDatesAsUnavailable(UUID visitPlaceId, List<LocalDate> dates, String reason) {
        for (LocalDate date : dates) {
            markDateAsUnavailable(visitPlaceId, date, reason); // reuse existing logic
        }
    }


    @Override
    public List<LocalDate> getClosedDatesByVisitPlace(UUID visitPlaceId) {
        // Fetch all VisitSchedules where isOpen = false and the place matches
        List<VisitSchedule> closedSchedules = visitScheduleRepository.findByVisitPlaceIdAndIsOpenFalse(visitPlaceId);

        // Extract and return just the dates
        return closedSchedules.stream()
                .map(VisitSchedule::getDate)
                .collect(Collectors.toList());
    }


//
//    @Override
//    public void markDateAsAvailable(UUID visitPlaceId, LocalDate date) {
//        VisitSchedule schedule = visitScheduleRepository.findByDateAndVisitPlaceId(date, visitPlaceId)
//                .orElseGet(() -> {
//                    VisitSchedule newSchedule = new VisitSchedule();
//                    newSchedule.setDate(date);
//                    newSchedule.setIsOpen(true); // explicitly available
//                    newSchedule.setPlaceStatuses(new ArrayList<>());
//                    return newSchedule;
//                });
//
//        schedule.setIsOpen(true);
//        schedule.setReasonForClosing(null);
//
//        VisitPlace visitPlace = (VisitPlace) visitPlaceRepository.findById(visitPlaceId)
//                .orElseThrow(() -> new VisitPlaceDoesNotExistException("Visit place not found"));
//
//        if (schedule.getPlaceStatuses().stream().noneMatch(ps -> ps.getVisitPlace().getId().equals(visitPlaceId))) {
//            VisitSchedulePlaceStatus placeStatus = VisitSchedulePlaceStatus.builder()
//                    .visitPlace(visitPlace)
//                    .visitSchedule(schedule)
//                    .isOpen(true)
//                    .reasonForClosing(null)
//                    .build();
//            schedule.getPlaceStatuses().add(placeStatus);
//        } else {
//            // If the placeStatus already exists, you might want to update it to open:
//            schedule.getPlaceStatuses().stream()
//                    .filter(ps -> ps.getVisitPlace().getId().equals(visitPlaceId))
//                    .forEach(ps -> {
//                        ps.setIsOpen(true);
//                        ps.setReasonForClosing(null);
//                    });
//        }
//
//        visitScheduleRepository.save(schedule);
//    }


//    @Override
//    public void markDateAsUnavailable(UUID visitPlaceId, LocalDate date, String reason) {
//        VisitSchedule schedule = visitScheduleRepository.findByDateAndVisitPlaceId(date, visitPlaceId)
//                .orElseGet(() -> {
//                    VisitSchedule newSchedule = new VisitSchedule();
//                    newSchedule.setDate(date);
//                    newSchedule.setIsOpen(false);
//                    newSchedule.setPlaceStatuses(new ArrayList<>());
//                    return newSchedule;
//                });
//
//        schedule.setIsOpen(false);
//        schedule.setReasonForClosing(reason);
//
//        VisitPlace visitPlace = (VisitPlace) visitPlaceRepository.findById(visitPlaceId)
//                .orElseThrow(() -> new VisitPlaceDoesNotExistException("Visit place not found"));
//
//        if (schedule.getPlaceStatuses().stream()
//                .noneMatch(ps -> ps.getVisitPlace().getId().equals(visitPlaceId))) {
//            VisitSchedulePlaceStatus placeStatus = VisitSchedulePlaceStatus.builder()
//                    .visitPlace(visitPlace)
//                    .visitSchedule(schedule)
//                    .status("UNAVAILABLE")
//                    .build();
//            schedule.getPlaceStatuses().add(placeStatus);
//        }
//
//        visitScheduleRepository.save(schedule);
//    }










//
//    @Override
//    public List<UnavailableDateDto> getUnavailableDates(UUID visitPlaceId, YearMonth month) {
//        LocalDate start = month.atDay(1);
//        LocalDate end = month.atEndOfMonth();
//
//        List<VisitSchedule> schedules = visitScheduleRepository
//                .findByVisitPlaceIdAndDateBetween(visitPlaceId, start, end);
//
//        List<UnavailableDateDto> unavailableDates = schedules.stream()
//                .filter(schedule -> !Boolean.TRUE.equals(schedule.getIsOpen())
//                        || schedule.getReasonForClosing() != null)
//                .filter(schedule -> schedule.getDate() != null)
//                .map(schedule -> new UnavailableDateDto(schedule.getDate(), schedule.getReasonForClosing()))
//                .collect(Collectors.toList());
//
//        // Track already added dates for performance
//        Set<LocalDate> addedDates = unavailableDates.stream()
//                .map(UnavailableDateDto::getDate)
//                .collect(Collectors.toSet());
//
//        List<Announcement> announcements = announcementRepository
//                .findEffectiveAnnouncementsForPlace(visitPlaceId, start.atStartOfDay(), end.atStartOfDay());
//
//        for (Announcement ann : announcements) {
//            LocalDate date = null;
//
//            if (ann.getVisitSchedule() != null && ann.getVisitSchedule().getDate() != null) {
//                date = ann.getVisitSchedule().getDate();
//            } else if (ann.getEffectiveDate() != null) {
//                date = ann.getEffectiveDate().toLocalDate();
//            }
//
//            if (date != null && addedDates.add(date)) {
//                unavailableDates.add(new UnavailableDateDto(date, ann.getSubject()));
//            }
//        }
//
//        return unavailableDates;
//    }
}
