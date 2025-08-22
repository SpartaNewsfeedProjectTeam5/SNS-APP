package org.example.snsapp.domain.notification.service;

import org.example.snsapp.domain.notification.dto.NotificationResponse;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.global.enums.NotificationContentType;

import java.util.List;

public interface NotificationService {
    void create(User fromUser, User toUser, NotificationContentType type, Long typeId, String message);

    List<NotificationResponse> findAllNotificationReceivedByEmail(String loginUserEmail);

    void delete(Long notificationId, String loginUserEmail);
}
