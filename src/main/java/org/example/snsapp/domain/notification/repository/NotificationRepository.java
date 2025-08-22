package org.example.snsapp.domain.notification.repository;

import org.example.snsapp.domain.notification.entity.Notification;
import org.example.snsapp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByToUser(User toUser);
}
