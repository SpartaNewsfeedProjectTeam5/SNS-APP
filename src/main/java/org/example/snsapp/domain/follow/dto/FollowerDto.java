package org.example.snsapp.domain.follow.dto;

import lombok.Getter;

@Getter
public class FollowerDto {

    private final String email;

    private final String username;

    public FollowerDto(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
