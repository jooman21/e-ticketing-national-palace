package com.example.e_ticketing.notification.domain.entity;

import com.example.e_ticketing.notification.domain.valueobject.NotificationStatus;
import com.example.e_ticketing.notification.domain.valueobject.NotificationType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    private LocalDateTime scheduledTime;

    private LocalDateTime sentTime;

    private Long referenceId;

    private String triggeredBy;

    private LocalDateTime createdAt;
}
