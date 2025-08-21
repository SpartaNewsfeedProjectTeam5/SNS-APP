package org.example.snsapp.domain.auth.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthLogoutResponse {
    private String message;
    private LocalDateTime loggedOutAt;

    public AuthLogoutResponse() {
        this.message = "로그아웃이 완료 되었습니다.";
        this.loggedOutAt = LocalDateTime.now();
    }
}
