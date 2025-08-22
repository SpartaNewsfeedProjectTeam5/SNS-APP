package org.example.snsapp.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.snsapp.domain.user.dto.UserUpdateRequest;
import org.example.snsapp.global.entity.AuditableEntity;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditableEntity {

    @Column(unique = true, nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 225)
    private String password;

    @Column(nullable = false, length = 4)
    private String username;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isResign;

    @Column(length = 255)
    private String profileImage;

    @Builder
    private User(String email, String password, String username, int age, boolean isResign, String profileImage) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.age = age;
        this.isResign = isResign;
        this.profileImage = profileImage;
    }

    public static User create(String email, String password, String username, int age, boolean isResign, String profileImage) {
        return User.builder()
                .email(email)
                .password(password)
                .username(username)
                .age(age)
                .isResign(false)
                .profileImage(profileImage)
                .build();
    }

    public void updateUserProfile(UserUpdateRequest dto) {
        this.username = dto.getUsername();
        this.age = dto.getAge();
        this.profileImage = dto.getProfileImage();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
