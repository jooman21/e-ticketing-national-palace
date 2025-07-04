package com.example.e_ticketing.ticketing.application.repository;


import com.example.e_ticketing.ticketing.domain.entity.VisitPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VisitPlaceRepository extends JpaRepository<VisitPlace, UUID> {


    Optional<Object> findByName(String name);


    List<VisitPlace> findAllByOrderByCreatedAtDesc();
}
