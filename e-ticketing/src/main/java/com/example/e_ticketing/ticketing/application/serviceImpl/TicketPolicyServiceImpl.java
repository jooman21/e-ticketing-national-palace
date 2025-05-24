package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.TicketPolicyDto;
import com.example.e_ticketing.ticketing.application.mapper.TicketPolicyMapper;
import com.example.e_ticketing.ticketing.application.repository.TicketPolicyRepository;
import com.example.e_ticketing.ticketing.application.service.TicketPolicyService;
import com.example.e_ticketing.ticketing.domain.entity.TicketPolicy;
import com.example.e_ticketing.ticketing.excpetion.TicketPolicyAlreadyExistsException;
import com.example.e_ticketing.ticketing.excpetion.TicketPolicyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketPolicyServiceImpl implements TicketPolicyService {

    private final TicketPolicyRepository ticketPolicyRepository;
    private final TicketPolicyMapper ticketPolicyMapper;


    @Override
    public TicketPolicyDto createPolicy(TicketPolicyDto dto) {
        // Check if the policy ID already exists
        if (dto.getId() != null && ticketPolicyRepository.existsById(dto.getId())) {
            throw new TicketPolicyAlreadyExistsException("Ticket policy with this ID already exists");
        }

        // Convert DTO to entity
        TicketPolicy entity = ticketPolicyMapper.MapTicketPolicyDtoToTicketPolicyEntity(dto);

        // Generate a new UUID if not set
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        // Save and return the mapped DTO
        TicketPolicy saved = ticketPolicyRepository.save(entity);
        return ticketPolicyMapper.MapTicketEntityToTicketPolicyDto(saved);
    }


    @Override
    public TicketPolicyDto getPolicy(UUID id) {
        TicketPolicy policy = ticketPolicyRepository.findById(id)
                .orElseThrow(() -> new TicketPolicyNotFoundException("Ticket policy not found"));
        return ticketPolicyMapper.MapTicketEntityToTicketPolicyDto(policy);
    }

    @Override
    public TicketPolicyDto updatePolicy(UUID id, TicketPolicyDto dto) {
        TicketPolicy policy = ticketPolicyRepository.findById(id)
                .orElseThrow(() -> new TicketPolicyNotFoundException("Ticket policy not found"));
        policy.setValidityDays(dto.getValidityDays());
        return ticketPolicyMapper.MapTicketEntityToTicketPolicyDto(ticketPolicyRepository.save(policy));
    }


}