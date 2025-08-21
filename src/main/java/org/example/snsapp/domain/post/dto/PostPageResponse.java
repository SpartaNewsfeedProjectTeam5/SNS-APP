package org.example.snsapp.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.snsapp.domain.post.entity.Post;

import java.time.LocalDateTime;

// 게시물 페이지 응답 DTO
@Getter
public class PostPageResponse {
    private final Long id;
    private final String email;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final int commentCount;
    private final int likeCount;

    @Builder
    private PostPageResponse(Long id,
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
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }

    public static PostPageResponse create(Long id,
                                          String email,
                                          String title,
                                          String content,
                                          LocalDateTime createdAt,
                                          LocalDateTime modifiedAt,
                                          int commentCount,
                                          int likeCount) {
        return PostPageResponse.builder()
                .id(id)
                .email(email)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .commentCount(commentCount)
                .likeCount(likeCount)
                .build();
    }

    public static PostPageResponse create(Post post, int commentCount, int likeCount) {
        return PostPageResponse.builder()
                .id(post.getId())
                .email(post.getUser().getEmail())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .commentCount(commentCount)
                .likeCount(likeCount)
                .build();
    }
}
