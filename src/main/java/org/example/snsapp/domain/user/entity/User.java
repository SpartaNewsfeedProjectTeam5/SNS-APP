package org.example.snsapp.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.snsapp.global.entity.AuditableEntity;

@Getter
@Entity
@NoArgsConstructor
public class User extends AuditableEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String username;

    private int age;

    private boolean isResign;

    private String profileImage;
}
