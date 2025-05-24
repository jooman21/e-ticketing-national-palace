package com.example.e_ticketing.ticketing.application.repository;

import com.example.e_ticketing.ticketing.domain.entity.QueueEntry;
import com.example.e_ticketing.ticketing.domain.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface QueueEntryRepository extends JpaRepository<QueueEntry, UUID> {
    int countByTimeSlotAndVisitSchedule_Date(TimeSlot timeSlot, LocalDate date);
}
