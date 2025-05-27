package com.example.e_ticketing.ticketing.application.service;

import com.example.e_ticketing.ticketing.application.dto.VisitorDto;
import com.example.e_ticketing.ticketing.domain.entity.Visitor;

public interface VisitorService {
    Visitor handleVisitor(VisitorDto dto);
}
