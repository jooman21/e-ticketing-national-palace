package com.example.e_ticketing.ticketing.application.mapper;

import com.example.e_ticketing.ticketing.application.dto.TicketPolicyDto;
import com.example.e_ticketing.ticketing.domain.entity.TicketPolicy;
import org.springframework.stereotype.Component;
@Component
public class TicketPolicyMapper {
    public TicketPolicyDto MapTicketEntityToTicketPolicyDto(TicketPolicy policy) {
        TicketPolicyDto dto = new TicketPolicyDto();
        dto.setId(policy.getId());
        dto.setValidityDays(policy.getValidityDays());
        dto.setCreatedAt(policy.getCreatedAt());
        dto.setUpdatedAt(policy.getUpdatedAt());
        return dto;
    }

    public TicketPolicy MapTicketPolicyDtoToTicketPolicyEntity(TicketPolicyDto dto) {
        TicketPolicy policy = new TicketPolicy();
        policy.setId(dto.getId());
        policy.setValidityDays(dto.getValidityDays());
        policy.setCreatedAt(dto.getCreatedAt());
        policy.setUpdatedAt(dto.getUpdatedAt());
        return policy;
    }
}
