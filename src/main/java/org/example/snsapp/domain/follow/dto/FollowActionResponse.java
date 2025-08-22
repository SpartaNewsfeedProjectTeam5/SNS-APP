package org.example.snsapp.domain.follow.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.snsapp.domain.follow.entity.Follow;

import java.time.LocalDateTime;

@Getter
public class FollowActionResponse {

    private final String message;
    private final LocalDateTime createdAt;

    @Builder
    private FollowActionResponse(String message, LocalDateTime createdAt) {
        this.message = message;
        this.createdAt = createdAt;
    }

    public static FollowActionResponse create(String message, LocalDateTime createdAt) {
        return FollowActionResponse.builder()
                .message(message)
                .createdAt(createdAt)
                .build();
    }
}
