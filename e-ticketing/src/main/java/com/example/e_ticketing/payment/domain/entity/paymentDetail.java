package com.example.e_ticketing.payment.domain.entity;

import com.example.e_ticketing.payment.domain.valueobject.ElectronicsPaymentMethod;
import com.example.e_ticketing.payment.domain.valueobject.PaymentMode;
import com.example.e_ticketing.payment.domain.valueobject.PaymentStatus;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class paymentDetail {
    private UUID paymentRequestId;

    private String paymentReference;

    private String email;

    private String phoneNumber;

    private float amount;

    private String transactionId;


    private ZonedDateTime expirationDate;

    private ZonedDateTime paidDate;

    private LocalDateTime transactionDate;
    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    @Enumerated(EnumType.STRING)
    private ElectronicsPaymentMethod electronicsPaymentMethod;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ElementCollection
    private List<UUID> ticketIds;

}
