package org.example.snsapp.domain.auth.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthDeleteResponse {
    private String message;
    private LocalDateTime deletedAt;

    public AuthDeleteResponse() {
        this.message = "회원 탈퇴가 완료 되었습니다.";
        this.deletedAt = LocalDateTime.now();
    }
}
