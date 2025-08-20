package org.example.snsapp.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthLoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
