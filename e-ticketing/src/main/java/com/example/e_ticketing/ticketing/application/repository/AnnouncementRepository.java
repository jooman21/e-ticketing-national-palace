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


    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Announcement a " +
            "JOIN a.visitPlaces p " +
            "WHERE a.announcementType = :type AND a.effectiveDate = :effectiveDate AND p.id = :visitPlaceId")
    boolean existsByTypeAndEffectiveDateAndVisitPlaceId(
            @Param("type") AnnouncementType type,
            @Param("effectiveDate") LocalDateTime effectiveDate,
            @Param("visitPlaceId") UUID visitPlaceId
    );






    List<Announcement> findByAnnouncementType(AnnouncementType type);

    @Query("SELECT a FROM Announcement a JOIN a.visitPlaces vp WHERE a.announcementType = :type AND DATE(a.effectiveDate) = :date AND vp.id = :visitPlaceId")
    List<Announcement> findByTypeAndDateAndVisitPlace(@Param("type") AnnouncementType type, @Param("date") LocalDate date, @Param("visitPlaceId") UUID visitPlaceId);

    @Query("SELECT a FROM Announcement a WHERE a.announcementType = :type AND DATE(a.effectiveDate) = :date")
    List<Announcement> findByTypeAndDate(@Param("type") AnnouncementType type, @Param("date") LocalDate date);

    @Query("SELECT a FROM Announcement a JOIN a.visitPlaces vp WHERE a.announcementType = :type AND vp.id = :visitPlaceId")
    List<Announcement> findByTypeAndVisitPlace(@Param("type") AnnouncementType type, @Param("visitPlaceId") UUID visitPlaceId);

}
