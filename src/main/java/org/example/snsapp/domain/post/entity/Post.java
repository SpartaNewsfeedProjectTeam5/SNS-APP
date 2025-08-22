package org.example.snsapp.domain.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.global.entity.AuditableEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends AuditableEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false, length = 30)
    private String title;
    private String content;
    @Column(name = "like_count")
    private int likeCount;
    @Column(name = "comment_count")
    private int commentCount;

    @Builder
    public Post(User user, String title, String content, int likeCount, int commentCount) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addLike() {
        this.likeCount++;
    }

    public void removeLike() {
        this.likeCount--;
    }

    public void addComment() {
        this.commentCount++;
    }

    public void removeComment() {
        this.commentCount--;
    }
}
