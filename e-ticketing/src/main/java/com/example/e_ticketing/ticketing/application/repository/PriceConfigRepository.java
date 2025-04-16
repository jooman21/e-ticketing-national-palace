package com.example.e_ticketing.ticketing.application.repository;

import com.example.e_ticketing.ticketing.domain.entity.PriceConfig;
import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import com.example.e_ticketing.ticketing.domain.valueobject.Residency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceConfigRepository extends JpaRepository<PriceConfig, Long> {
    boolean existsByName(String name);

    boolean existsByTicketTypeAndResidency(TicketType ticketType, Residency residency);
}
