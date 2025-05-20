package com.example.e_ticketing.ticketing.application.repository;

import com.example.e_ticketing.ticketing.domain.entity.VisitSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VisitScheduleRepository extends JpaRepository<VisitSchedule, UUID> {

    @Query("SELECT DISTINCT vs FROM VisitSchedule vs " +
            "JOIN vs.placeStatuses vps " +
            "WHERE vps.visitPlace.id = :visitPlaceId " +
            "AND vs.date BETWEEN :start AND :end")
    List<VisitSchedule> findByVisitPlaceIdAndDateBetween(
            @Param("visitPlaceId") UUID visitPlaceId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);

    @Query("""
        SELECT vs FROM VisitSchedule vs
        JOIN vs.placeStatuses vps
        WHERE vs.date = :date AND vps.visitPlace.id = :visitPlaceId
        """)
    Optional<VisitSchedule> findByDateAndVisitPlaceId(
            @Param("date") LocalDate date,
            @Param("visitPlaceId") UUID visitPlaceId);



    @Query("SELECT DISTINCT vs FROM VisitSchedule vs " +
            "JOIN vs.placeStatuses vps " +
            "WHERE vps.visitPlace.id = :visitPlaceId " +
            "AND vs.isOpen = false")
    List<VisitSchedule> findByVisitPlaceIdAndIsOpenFalse(@Param("visitPlaceId") UUID visitPlaceId);

    Optional<VisitSchedule> findByDate(LocalDate date);
}
