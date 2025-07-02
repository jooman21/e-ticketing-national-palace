package com.example.e_ticketing.ticketing.application.repository;

import com.example.e_ticketing.ticketing.domain.entity.VisitSchedule;
import com.example.e_ticketing.ticketing.domain.entity.VisitSchedulePlaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitSchedulePlaceStatusRepository extends JpaRepository<VisitSchedulePlaceStatus, Long> {
    @Query("SELECT vs FROM VisitSchedule vs LEFT JOIN FETCH vs.placeStatuses WHERE vs.date = :date")
    List<VisitSchedule> findByDateWithStatuses(@Param("date") LocalDate date);
}
