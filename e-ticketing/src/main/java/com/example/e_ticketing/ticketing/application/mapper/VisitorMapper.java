package com.example.e_ticketing.ticketing.application.mapper;

import com.example.e_ticketing.ticketing.application.dto.VisitorDto;
import com.example.e_ticketing.ticketing.domain.entity.Visitor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VisitorMapper {
    public static VisitorDto  MapVisitorEntityToVisitorDto(Visitor visitor) {
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

    public static Visitor VisitorDtoToVisitorEntity(VisitorDto dto) {
        if (dto == null) return null;

        return Visitor.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .nationality(dto.getNationality())
                .residency(dto.getResidency())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
