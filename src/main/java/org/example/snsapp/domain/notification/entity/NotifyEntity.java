package org.example.snsapp.domain.notification.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.snsapp.domain.notification.Enum.NotificationContentType;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotifyEntity extends BaseEntity{

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name = "from_id")
    @Column(nullable=false)
    private User fromUser; //보낸 유저

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name = "to_id")
    @Column(nullable=false)
    private User toUser; //받는 유저

    @Column(nullable=false, length=30)
    @Enumerated(EnumType.STRING)
    private NotificationContentType type; //타입

    @Column(nullable=false, length = 250)
    private String message; //메세지

    public NotifyEntity(Long fromUser, Long toUser, NotificationContentType type, String message) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.type = type;
        this.message = message;
    }
}

