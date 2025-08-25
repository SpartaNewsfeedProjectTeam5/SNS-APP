package org.example.snsapp.domain.notification.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.snsapp.domain.notification.dto.NotificationResponse;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.global.entity.BaseEntity;
import org.example.snsapp.global.enums.NotificationContentType;

@Entity
@Getter
@Table(name="notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name = "from_id")
    private User fromUser; //보낸 유저

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name = "to_id")
    private User toUser; //받는 유저

    @Column(nullable=false, length=30)
    @Enumerated(EnumType.STRING)
    private NotificationContentType type; //타입

    @Column(name = "type_id", nullable = false)
    private Long typeId;

    @Column(nullable = false, length = 255)
    private String message; //메세지

    @Builder
    private Notification(User fromUser, User toUser, NotificationContentType type, Long typeId, String message) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.type = type;
        this.typeId = typeId;
        this.message = message;
    }

    public static Notification create(User fromUser, User toUser, NotificationContentType type, Long typeId, String message) {
        return Notification.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .type(type)
                .typeId(typeId)
                .message(message)
                .build();
    }
}

