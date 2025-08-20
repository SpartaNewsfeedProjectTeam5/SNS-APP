package org.example.snsapp.domain.follow.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="follow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowEntity extends BaseEntity{
    //id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //팔로워 id
    private String followerId;

    //팔로잉 id
    private String followingId;

    public FollowEntity(String followerId, String followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }
}
