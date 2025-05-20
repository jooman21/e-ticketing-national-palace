package com.example.e_ticketing.ticketing.application.dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnavailableDateRangeRequest {
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private String reasonForClosing;
}
