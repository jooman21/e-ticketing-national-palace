package com.example.e_ticketing.ticketing.application.repository;

import com.example.e_ticketing.ticketing.domain.entity.VisitSchedulePlaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitSchedulePlaceStatusRepository extends JpaRepository<VisitSchedulePlaceStatus, Long> {
}
