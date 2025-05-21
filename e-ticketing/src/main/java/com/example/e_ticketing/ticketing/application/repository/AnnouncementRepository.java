package com.example.e_ticketing.ticketing.application.repository;

import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import com.example.e_ticketing.ticketing.domain.valueobject.AnnouncementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {

    @Query("""
        SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
        FROM Announcement a
        JOIN a.visitPlaces vp
        WHERE a.announcementType = :announcementType
          AND a.effectiveDate = :effectiveDate
          AND vp.id IN :visitPlaceIds
    """)
    boolean existsByAnnouncementTypeAndEffectiveDateAndVisitPlaceIds(
            @Param("announcementType") AnnouncementType announcementType,
            @Param("effectiveDate") LocalDateTime effectiveDate,
            @Param("visitPlaceIds") List<UUID> visitPlaceIds);
}
