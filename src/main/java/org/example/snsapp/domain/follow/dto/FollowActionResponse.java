package org.example.snsapp.domain.follow.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.example.snsapp.domain.follow.entity.Follow;

import java.time.LocalDateTime;

@Getter
public class FollowActionResponse {

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final LocalDateTime createdAt;

    @Builder
    private FollowActionResponse(String message, LocalDateTime createdAt) {
        this.message = message;
        this.createdAt = createdAt;
    }

    public static FollowActionResponse ofFollow(String message, LocalDateTime createdAt) {
        return FollowActionResponse.builder()
                .message(message)
                .createdAt(createdAt)
                .build();
    }

    public static FollowActionResponse ofUnfollow(String message) {
        return FollowActionResponse.builder()
                .message(message)
                .build();
    }
}
