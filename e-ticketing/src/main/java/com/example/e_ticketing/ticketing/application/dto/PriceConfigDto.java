package com.example.e_ticketing.ticketing.application.dto;

import com.example.e_ticketing.ticketing.domain.entity.TicketType;
import com.example.e_ticketing.ticketing.domain.valueobject.Currency;
import com.example.e_ticketing.ticketing.domain.valueobject.Residency;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceConfigDto {
    private Long id;
    private String name;
    private Residency residency;
    private Currency currency;
    private Double price;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
