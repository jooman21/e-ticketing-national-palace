package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.TicketPolicyDto;

import java.util.UUID;

public interface TicketPolicyService {


    TicketPolicyDto createPolicy(TicketPolicyDto dto);

    TicketPolicyDto getPolicy(UUID id);

    TicketPolicyDto updatePolicy(UUID id, TicketPolicyDto dto);
}
