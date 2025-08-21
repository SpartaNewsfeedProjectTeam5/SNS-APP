package org.example.snsapp.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.snsapp.domain.user.entity.User;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class AuthSignUpResponse {
    private String message;
    private String email;
    private LocalDateTime createdAt;

    public static AuthSignUpResponse create(User user) {
        return AuthSignUpResponse.builder()
                .message("회원가입이 완료 되었습니다.")
                .email(user.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
