package org.example.snsapp.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.snsapp.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class UserUpdateResponse {

    private final String email;
    private final String username;
    private final int age;
    private final String profileImage;
    private final LocalDateTime modifiedAt;

    @Builder
    private UserUpdateResponse(String email, String username, int age, String profileImage, LocalDateTime modifiedAt) {
        this.email = email;
        this.username = username;
        this.age = age;
        this.profileImage = profileImage;
        this.modifiedAt = modifiedAt;
    }

    public static UserUpdateResponse create(User user) {
        return UserUpdateResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .age(user.getAge())
                .profileImage(user.getProfileImage())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
