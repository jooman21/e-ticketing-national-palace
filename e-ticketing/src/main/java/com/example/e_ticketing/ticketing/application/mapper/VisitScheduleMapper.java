package com.example.e_ticketing.ticketing.application.mapper;


import com.example.e_ticketing.ticketing.application.dto.VisitScheduleDto;
import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;
import com.example.e_ticketing.ticketing.domain.entity.VisitSchedule;
import com.example.e_ticketing.ticketing.domain.entity.VisitSchedulePlaceStatus;

import java.time.LocalDateTime;
import java.util.List;

public class VisitScheduleMapper {
    public VisitSchedule VisitScheduleDtoToEntity(VisitScheduleDto dto, List<VisitPlace> visitPlaces) {
        VisitSchedule schedule = VisitSchedule.builder()
                .date(dto.getDate())
                .isOpen(dto.getIsOpen())
                .reasonForClosing(dto.getReasonForClosing())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<VisitSchedulePlaceStatus> statuses = visitPlaces.stream().map(place ->
                VisitSchedulePlaceStatus.builder()
                        .visitSchedule(schedule)
                        .visitPlace(place)
                        .isOpen(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).toList();

        schedule.setPlaceStatuses(statuses);
        return schedule;
    }

}


//public class VisitPlaceMapper {
//
//    //Converts the Visit Place entity (from the DB) into a VisitPlaceDto to send back to the client.
//    public static VisitPlaceDto MapVisitPlaceToVisitPlaceDto(VisitPlace visitPlace) {
//        return VisitPlaceDto.builder()
//                .id(visitPlace.getId())
//                .name(visitPlace.getName())
//                .available(visitPlace.getIsAvailable())
//                .createdAt(visitPlace.getCreatedAt())
//                .updatedAt(visitPlace.getUpdatedAt())
//                .build();
//    }
//    public static VisitPlace MapVisitPlaceDtoToVisitPlace(VisitPlaceDto dto) {
//        // Converts the VisitPlaceDto (received from the client) into a VisitPlace entity to save into the database.
//        VisitPlace place = new VisitPlace();
//        place.setId(dto.getId());
//        place.setName(dto.getName());
//        place.setIsAvailable(dto.getAvailable());
//        place.setCreatedAt(dto.getCreatedAt());
//        place.setUpdatedAt(dto.getUpdatedAt());
//        return place;
//    }
//}