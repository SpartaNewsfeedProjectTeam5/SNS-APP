package org.example.snsapp.domain.like.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.global.entity.BaseEntity;
import org.example.snsapp.global.enums.LikeContentType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private LikeContentType type;
    @Column(name = "type_id", nullable = false)
    private Long typeId;

    public Like(User user, LikeContentType type, Long typeId) {
        this.user = user;
        this.type = type;
        this.typeId = typeId;
    }
}
