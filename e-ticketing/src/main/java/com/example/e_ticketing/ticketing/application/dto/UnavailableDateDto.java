package com.example.e_ticketing.ticketing.application.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnavailableDateDto {
    private LocalDate date;
    private String reasonForClosing;
}
