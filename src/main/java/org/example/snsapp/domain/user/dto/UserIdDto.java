package org.example.snsapp.domain.user.dto;

import lombok.Getter;

@Getter
public class UserIdDto {
    private final Long id;

    private UserIdDto(Long id) {
        this.id = id;
    }

    public static UserIdDto create(Long id) {
        return new UserIdDto(id);
    }
}
