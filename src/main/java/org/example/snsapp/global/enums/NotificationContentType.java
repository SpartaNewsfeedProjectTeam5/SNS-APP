package org.example.snsapp.global.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum NotificationContentType {
    LIKE("좋아요"),
    COMMENT("댓글"),
    FOLLOW("팔로우");
    
    private final String name;
}
