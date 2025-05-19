package com.example.e_ticketing.ticketing.application.dto;


import lombok.*;


import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitPlaceDto {
    private UUID id;
    private String name;
}
