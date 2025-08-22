package org.example.snsapp.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.example.snsapp.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class UserProfileResponse {

    private final String email;
    private final String username;
    private final int age;
    private final String profileImage;
    private final int followerCount;
    private final int followingCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final LocalDateTime modifiedAt;

    @Builder
    private UserProfileResponse(String email, String username, int age, String profileImage, int followerCount, int followingCount, LocalDateTime modifiedAt) {
        this.email = email;
        this.username = username;
        this.age = age;
        this.profileImage = profileImage;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.modifiedAt = modifiedAt;
    }

    public static UserProfileResponse create(User user, int followerCount, int followingCount) {
        return UserProfileResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .age(user.getAge())
                .profileImage(user.getProfileImage())
                .followerCount(followerCount)
                .followingCount(followingCount)
                .build();
    }

    public static UserProfileResponse createForUpdate(User user, int followerCount, int followingCount) {
        return UserProfileResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .age(user.getAge())
                .profileImage(user.getProfileImage())
                .followerCount(followerCount)
                .followingCount(followingCount)
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
