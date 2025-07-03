package com.example.e_ticketing.ticketing.application.response;

import com.example.e_ticketing.ticketing.application.dto.TicketDto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketGroupBookingResponse {
    private String ticketTypeName;
    private String visitorType;
    private int totalTickets;
    private BigDecimal totalPrice;
    private List<TicketDto> tickets;
}
