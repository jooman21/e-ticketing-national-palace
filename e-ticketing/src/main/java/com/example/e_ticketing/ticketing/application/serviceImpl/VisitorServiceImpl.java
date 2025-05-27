package com.example.e_ticketing.ticketing.application.serviceImpl;

import com.example.e_ticketing.ticketing.application.dto.VisitorDto;
import com.example.e_ticketing.ticketing.application.repository.VisitorRepository;
import com.example.e_ticketing.ticketing.application.service.VisitorService;
import com.example.e_ticketing.ticketing.domain.entity.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {
    private final VisitorRepository visitorRepository;
    @Override
    public Visitor handleVisitor(VisitorDto dto) {
        return (Visitor) visitorRepository.findByEmail(dto.getEmail())
                .orElseGet(() -> {
                    Visitor newVisitor = new Visitor();
                    newVisitor.setFullName(dto.getFullName());
                    newVisitor.setEmail(dto.getEmail());
                    newVisitor.setPhoneNumber(dto.getPhoneNumber());
                    newVisitor.setNationality(dto.getNationality());
                    newVisitor.setResidency(dto.getResidency());
                    return visitorRepository.save(newVisitor);
                });
    }


}