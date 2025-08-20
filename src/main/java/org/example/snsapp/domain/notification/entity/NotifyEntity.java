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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //id

    private Long fromId; //보낸 유저

    private Long toId; //받는 유저

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private NotificationContentType type; //타입

    private String message; //메세지

    private LocalDateTime createdAt; //생성일

    @ManyToOne
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity; //유저 엔티티와 다대일 관계

    public NotifyEntity(Long fromId, Long toId, NotificationContentType type, String message, LocalDateTime createdAt) {
        this.fromId = fromId;
        this.toId = toId;
        this.type = type;
        this.message = message;
        this.createdAt = createdAt;
    }
}

