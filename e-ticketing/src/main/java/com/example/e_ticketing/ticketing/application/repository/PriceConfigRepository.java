package com.example.e_ticketing.ticketing.application.repository;

import com.example.e_ticketing.ticketing.domain.entity.PriceConfig;
import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import com.example.e_ticketing.ticketing.domain.valueobject.Residency;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceConfigRepository extends JpaRepository<PriceConfig, UUID> {
    boolean existsByTicketTypeAndResidencyAndVisitorType(TicketType ticketType, Residency residency, String visitorType);



    Optional<PriceConfig> findByTicketTypeAndResidencyAndVisitorType(
            TicketType ticketType, Residency residency, String visitorType);

}