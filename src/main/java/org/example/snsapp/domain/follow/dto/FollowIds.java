package org.example.snsapp.domain.follow.dto;

import lombok.Getter;

@Getter
public class FollowIds {

    private final Long followerId;
    private final Long followingId;

    private FollowIds(Long followerId, Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

    public static FollowIds create(Long followerId, Long followingId) {
        return new FollowIds(followerId, followingId);
    }
}
