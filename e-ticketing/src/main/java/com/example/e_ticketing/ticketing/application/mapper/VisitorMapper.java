package com.example.e_ticketing.ticketing.application.mapper;

import com.example.e_ticketing.ticketing.application.dto.VisitorDto;
import com.example.e_ticketing.ticketing.domain.entity.Visitor;
import org.springframework.stereotype.Component;

@Component
public class VisitorMapper {
    public VisitorDto MapVisitorEntityToVisitorDto(Visitor visitor) {
        if (visitor == null) return null;

        return VisitorDto.builder()
                .id(visitor.getId())
                .fullName(visitor.getFullName())
                .email(visitor.getEmail())
                .phoneNumber(visitor.getPhoneNumber())
                .nationality(visitor.getNationality())
                .residency(visitor.getResidency())
                .createdAt(visitor.getCreatedAt())
                .updatedAt(visitor.getUpdatedAt())
                .build();
    }

    public Visitor MapVisitorDtoToVisitorEntity(VisitorDto dto) {
        if (dto == null) return null;

        Visitor visitor = new Visitor();
        visitor.setId(dto.getId());
        visitor.setFullName(dto.getFullName());
        visitor.setEmail(dto.getEmail());
        visitor.setPhoneNumber(dto.getPhoneNumber());
        visitor.setNationality(dto.getNationality());
        visitor.setResidency(dto.getResidency());
        visitor.setCreatedAt(dto.getCreatedAt());
        visitor.setUpdatedAt(dto.getUpdatedAt());
        return visitor;
    }
}
