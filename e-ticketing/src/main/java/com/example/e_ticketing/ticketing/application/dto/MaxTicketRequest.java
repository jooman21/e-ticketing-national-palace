package com.example.e_ticketing.ticketing.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaxTicketRequest {
    private Integer maxTickets;
}
