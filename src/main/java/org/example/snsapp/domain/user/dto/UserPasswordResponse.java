package org.example.snsapp.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.snsapp.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class UserPasswordResponse {

    private final String message;
    private final LocalDateTime modifiedAt;

    private UserPasswordResponse(String message, LocalDateTime modifiedAt) {
        this.message = message;
        this.modifiedAt = modifiedAt;
    }

    public static UserPasswordResponse create(String message, LocalDateTime modifiedAt) {
        return new UserPasswordResponse(message, modifiedAt);
    }
}
