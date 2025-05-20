package com.example.e_ticketing.ticketing.application.dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnavailableDatesRequest {
    @NotNull
    private List<LocalDate> dates;
    private String reasonForClosing;
}
