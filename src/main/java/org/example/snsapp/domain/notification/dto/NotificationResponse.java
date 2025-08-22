package org.example.snsapp.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.snsapp.domain.notification.entity.Notification;

import java.time.LocalDateTime;

@Getter
public class NotificationResponse {
    private final String message;
    private final LocalDateTime createdAt;
    @Builder
    private NotificationResponse (
            String message,
            LocalDateTime createdAt)
    {
        this.message = message;
        this.createdAt = createdAt;
    }

    public static NotificationResponse create(String message,LocalDateTime createdAt) {
        return NotificationResponse.builder()
                .message(message)
                .createdAt(createdAt)
                .build();
    }

    public static NotificationResponse create(Notification notification) {
        return NotificationResponse.builder()
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
