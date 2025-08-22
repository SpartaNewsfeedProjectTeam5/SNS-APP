package org.example.snsapp.domain.like.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.snsapp.domain.like.entity.Like;
import org.example.snsapp.global.enums.LikeContentType;

import java.time.LocalDateTime;

@Getter
public class LikeResponse {
    private final Long id;
    private final String email;
    private final LikeContentType type;
    private final Long typeId;
    private final LocalDateTime createdAt;

    @Builder
    private LikeResponse(Long id, String email, LikeContentType type, Long typeId, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.type = type;
        this.typeId = typeId;
        this.createdAt = createdAt;
    }

    public static LikeResponse create(Long id, String email, LikeContentType type, Long typeId, LocalDateTime createdAt) {
        return LikeResponse.builder()
                .id(id)
                .email(email)
                .type(type)
                .typeId(typeId)
                .createdAt(createdAt)
                .build();
    }

    public static LikeResponse create(Like like) {
        return LikeResponse.builder()
                .id(like.getId())
                .email(like.getUser().getEmail())
                .type(like.getType())
                .typeId(like.getTypeId())
                .createdAt(like.getCreatedAt())
                .build();
    }
}
