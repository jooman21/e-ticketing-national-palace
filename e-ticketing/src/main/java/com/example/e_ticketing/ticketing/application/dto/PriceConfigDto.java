package com.example.e_ticketing.ticketing.application.dto;


import com.example.e_ticketing.ticketing.domain.valueobject.Currency;
import com.example.e_ticketing.ticketing.domain.valueobject.Residency;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceConfigDto {
    private UUID id;
    private String name;
    private Residency residency;
    private String visitorType;
    private Currency currency;
    private Double price;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
