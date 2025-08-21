package org.example.snsapp.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.snsapp.global.entity.AuditableEntity;

@Getter
@Entity
@NoArgsConstructor
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

    private String profileImage;

    @Builder
    public User(String email, String password, String username, int age, boolean isResign, String profileImage) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.age = age;
        this.isResign = isResign;
        this.profileImage = profileImage;
    }
}
