package org.example.snsapp.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.snsapp.domain.post.entity.Post;

import java.time.LocalDateTime;

// 게시물 기본 응답 DTO
@Getter
public class PostResponse {
    private final Long id;
    private final String email;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final int commentCount;
    private final int likeCount;

    @Builder
    private PostResponse(
            Long id,
            String email,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            int commentCount,
            int likeCount) {
        this.id = id;
        this.email = email;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static PostResponse create(
            Long id,
            String email,
            String title,
            String content,
            int commentCount,
            int likeCount,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        return PostResponse.builder()
                .id(id)
                .email(email)
                .title(title)
                .content(content)
                .commentCount(commentCount)
                .likeCount(likeCount)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public static PostResponse create(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .email(post.getUser().getEmail())
                .title(post.getTitle())
                .content(post.getContent())
                .commentCount(post.getCommentCount())
                .likeCount(post.getLikeCount())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
