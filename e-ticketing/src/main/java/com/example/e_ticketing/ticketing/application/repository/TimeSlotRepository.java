package com.example.e_ticketing.ticketing.application.repository;

import com.example.e_ticketing.ticketing.domain.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, UUID> {

    List<TimeSlot> findByIsActiveTrueOrderByStartTimeAsc();

    List<TimeSlot> findAllByIsActiveTrue();
}
