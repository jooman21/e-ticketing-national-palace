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



    Optional<VisitSchedule> findByDate(LocalDate date);

    // Find all VisitSchedule where isOpen = false
    @Query("SELECT v.date FROM VisitSchedule v WHERE v.isOpen = false")
    List<LocalDate> findAllClosedDates();



}
