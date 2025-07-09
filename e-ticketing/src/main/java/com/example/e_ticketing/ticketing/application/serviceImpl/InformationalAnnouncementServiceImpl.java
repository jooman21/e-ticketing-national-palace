package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.InformationalAnnouncementDto;
import com.example.e_ticketing.ticketing.application.repository.AnnouncementRepository;
import com.example.e_ticketing.ticketing.application.service.InformationalAnnouncementService;
import com.example.e_ticketing.ticketing.domain.entity.Announcement;
import com.example.e_ticketing.ticketing.domain.valueobject.AnnouncementType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InformationalAnnouncementServiceImpl implements InformationalAnnouncementService {
    private final AnnouncementRepository announcementRepository;
    @Override
    public InformationalAnnouncementDto createInformationalAnnouncement(InformationalAnnouncementDto dto) {
        // Ensure the type is always INFORMATIONAL_NOTICE
        dto.setAnnouncementType(AnnouncementType.INFORMATIONAL_NOTICE);

        // Convert DTO to entity
        Announcement entity = new Announcement();
        entity.setSubject(dto.getSubject());
        entity.setMessage(dto.getMessage());
        entity.setAnnouncementType(dto.getAnnouncementType());

        // Save the entity
        Announcement saved = announcementRepository.save(entity);

        // Convert back to DTO
        InformationalAnnouncementDto response = new InformationalAnnouncementDto();
        response.setId(saved.getId());
        response.setSubject(saved.getSubject());
        response.setMessage(saved.getMessage());
        response.setAnnouncementType(saved.getAnnouncementType());

        return response;
    }

}
