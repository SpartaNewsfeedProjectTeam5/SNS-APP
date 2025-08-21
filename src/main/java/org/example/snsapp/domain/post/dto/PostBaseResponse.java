package org.example.snsapp.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.snsapp.domain.post.entity.Post;

import java.time.LocalDateTime;

// 게시물 기본 응답 DTO
@Getter
public class PostBaseResponse {
    private final Long id;
    private final String email;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    private PostBaseResponse(Long id, String email, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.email = email;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static PostBaseResponse create(Long id, String email, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return PostBaseResponse.builder()
                .id(id)
                .email(email)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public static PostBaseResponse create(Post post) {
        return PostBaseResponse.builder()
                .id(post.getId())
                .email(post.getUser().getEmail())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
