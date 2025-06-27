package com.example.e_ticketing.ticketing.application.repository;

import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, UUID> {
    @Query("SELECT t FROM TicketType t WHERE t.name = :name")
    Optional<TicketType> findByName(@Param("name") String name);


    List<TicketType> findAllByOrderByCreatedAtDesc();
}
