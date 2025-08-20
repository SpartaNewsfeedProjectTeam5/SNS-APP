package org.example.snsapp.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.snsapp.domain.post.entity.Post;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.global.entity.AuditableEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "comment", nullable = false, length = 100)
    private String comment;

    public Comment(User user, Post post, String comment) {
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

}
