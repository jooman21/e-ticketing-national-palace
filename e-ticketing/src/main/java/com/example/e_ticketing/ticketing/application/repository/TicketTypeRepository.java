package com.example.e_ticketing.ticketing.application.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TicketTypeRepository {
    Optional<Object> findByName(String name);
}
