package org.example.snsapp.domain.auth.dto;

import java.time.LocalDateTime;

public class AuthLogoutResponse {
    private String message;
    private LocalDateTime loggedOutAt;

    public AuthLogoutResponse(String message) {
        this.message = message;
        this.loggedOutAt = LocalDateTime.now();
    }
}
