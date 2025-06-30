//package com.example.e_ticketing.ticketing.application.mapper;
//
//import com.example.e_ticketing.ticketing.application.dto.PriceConfigDto;
//import com.example.e_ticketing.ticketing.application.dto.PriceConfigForStudentDto;
//import com.example.e_ticketing.ticketing.domain.entity.PriceConfig;
//import com.example.e_ticketing.ticketing.domain.entity.TicketType;
//
//public class PriceConfigForStudentMapper {
//    //Converts the PriceConfig entity (from the DB) into a PriceConfigDto to send back to the client.
//    public static PriceConfigForStudentDto MapPriceConfigForStudentToPriceConfigForStudentDto(PriceConfig priceConfig) {
//        return PriceConfigForStudentDto.builder()
//                .id(priceConfig.getId())
//                .name(priceConfig.getTicketType() != null ? priceConfig.getTicketType().getName() : null)
//                .studentType(priceConfig.getStudentType())
//                .currency(priceConfig.getCurrency())
//                .price(priceConfig.getPrice())
//                .active(priceConfig.getActive())
//                .createdAt(priceConfig.getCreatedAt())
//                .updatedAt(priceConfig.getUpdatedAt())
//                .build();
//    }
//
//    public static PriceConfig MapPriceConfigForStudentDtoToPriceConfigForStudent(PriceConfigForStudentDto dto, TicketType ticketType) {
//        // Converts the PriceConfigDto (received from the client) into a PriceConfig entity to save into the database.
//        PriceConfig config = new PriceConfig();
//        config.setId(dto.getId());
//        config.setTicketType(ticketType);
//        config.setStudentType(dto.getStudentType());
//        config.setCurrency(dto.getCurrency());
//        config.setPrice(dto.getPrice());
//        config.setActive(dto.getActive());
//        config.setCreatedAt(dto.getCreatedAt());
//       // config.setUpdatedAt(dto.getUpdatedAt());
//        return config;
//    }
//}
