package org.example.snsapp.domain.auth.dto;

import java.time.LocalDateTime;

public class AuthLoginResponse {
    private String message;
    private String email;
    private LocalDateTime LoginTime;

    public AuthLoginResponse(String email) {
        this.message = "로그인이 완료 되었습니다.";
        this.email = email;
        this.LoginTime = LocalDateTime.now();
    }
}
