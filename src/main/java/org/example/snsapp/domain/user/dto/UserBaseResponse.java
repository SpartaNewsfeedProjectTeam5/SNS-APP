package org.example.snsapp.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.snsapp.domain.user.entity.User;

@Getter
public class UserBaseResponse {

    private final String email;
    private final String username;
    private final int age;
    private final String profileImage;
    private final int followerCount;
    private final int followingCount;

    @Builder
    private UserBaseResponse(String email, String username, int age, String profileImage, int followerCount, int followingCount) {
        this.email = email;
        this.username = username;
        this.age = age;
        this.profileImage = profileImage;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

    public static UserBaseResponse create(User user, int followerCount, int followingCount) {
        return UserBaseResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .age(user.getAge())
                .profileImage(user.getProfileImage())
                .followerCount(followerCount)
                .followingCount(followingCount)
                .build();
    }
}
