package com.example.e_ticketing.ticketing.application.dto;
import com.example.e_ticketing.ticketing.domain.valueobject.Residency;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitorDto {
    private UUID id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String nationality;
    private Residency residency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
