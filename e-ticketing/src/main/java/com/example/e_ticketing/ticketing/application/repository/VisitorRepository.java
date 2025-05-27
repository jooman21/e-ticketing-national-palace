package com.example.e_ticketing.ticketing.application.repository;


import com.example.e_ticketing.ticketing.domain.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, UUID> {
    <T> Optional<T> findByEmail(String email);
}
