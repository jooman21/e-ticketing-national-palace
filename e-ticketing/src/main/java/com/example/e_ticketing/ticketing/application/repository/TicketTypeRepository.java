package com.example.e_ticketing.ticketing.application.repository;

import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {
    Optional<Object> findByName(String name);

    Optional<Object> findById(UUID id);
}
