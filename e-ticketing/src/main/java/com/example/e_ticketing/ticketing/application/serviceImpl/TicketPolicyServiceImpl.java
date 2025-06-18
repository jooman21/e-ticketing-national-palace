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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketPolicyServiceImpl implements TicketPolicyService {

    private final TicketPolicyRepository ticketPolicyRepository;
    private final TicketPolicyMapper ticketPolicyMapper;


    @Override
    public TicketPolicyDto createPolicy(TicketPolicyDto dto) {
        // Prevent duplicate creation if ID is supplied
        if (dto.getId() != null && ticketPolicyRepository.existsById(dto.getId())) {
            throw new TicketPolicyAlreadyExistsException("Ticket policy with this ID already exists");
        }

        // Map DTO to entity (do not manually set ID)
        TicketPolicy entity = ticketPolicyMapper.MapTicketPolicyDtoToTicketPolicyEntity(dto);

        // Save and return DTO
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
    public List<TicketPolicyDto> getAllPolicies() {
        List<TicketPolicy> policies = ticketPolicyRepository.findAll();

        return policies.stream()
                .map(ticketPolicyMapper::MapTicketEntityToTicketPolicyDto)
                .toList();
    }


    @Override
    public TicketPolicyDto updatePolicy(UUID id, TicketPolicyDto dto) {
        TicketPolicy policy = ticketPolicyRepository.findById(id)
                .orElseThrow(() -> new TicketPolicyNotFoundException("Ticket policy not found"));
        policy.setValidityDays(dto.getValidityDays());
        return ticketPolicyMapper.MapTicketEntityToTicketPolicyDto(ticketPolicyRepository.save(policy));
    }

    @Override
    public void deletePolicy(UUID id) {
        TicketPolicy policy = ticketPolicyRepository.findById(id)
                .orElseThrow(() -> new TicketPolicyNotFoundException("Ticket policy not found"));
        ticketPolicyRepository.delete(policy);
    }

}