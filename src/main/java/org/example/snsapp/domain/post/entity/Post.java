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
    @Column(name ="comment_count",nullable = false)
    private int commentCount;
    @Column(name = "like_count",nullable = false)
    private int likeCount;

    @Builder
    public Post(User user, String title, String content ) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseLikeCount() {
        this.likeCount=this.likeCount+1;
    }

    public void decreaseLikeCount() {
        this.likeCount=this.likeCount-1;
    }

    public void increaseCommentCount() {
        this.commentCount=this.commentCount+1;
    }

    public void decreaseCommentCount() {
        this.commentCount=this.commentCount-1;
    }
}
