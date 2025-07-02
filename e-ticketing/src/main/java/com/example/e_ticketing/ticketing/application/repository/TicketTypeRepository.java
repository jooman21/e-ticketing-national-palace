package com.example.e_ticketing.ticketing.application.repository;

import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, UUID> {
    @Query("SELECT t FROM TicketType t WHERE t.name = :name")
    Optional<TicketType> findByName(@Param("name") String name);


    List<TicketType> findAllByOrderByCreatedAtDesc();


    @Query("""
    SELECT DISTINCT tt FROM TicketType tt
    JOIN tt.visitPlaces vp
    WHERE vp.id IN :placeIds
    ORDER BY tt.createdAt DESC
    """)
    List<TicketType> findByVisitPlaceIds(@Param("placeIds") Set<UUID> placeIds);
}
