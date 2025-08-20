package org.example.snsapp.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.auth.dto.*;
import org.example.snsapp.domain.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService signUpService;

    @PostMapping("/login")
    public ResponseEntity signIn(@Valid @RequestBody AuthLoginRequest loginRequest, HttpServletRequest request) {
        return signUpService.login(loginRequest, request);
    }

    @PostMapping("/signUp")
    public AuthSignUpResponse signUp (@Valid @RequestBody AuthSignUpRequest authRequest, HttpServletRequest request) {
        return signUpService.signUp(authRequest, request);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthLogoutResponse> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return ResponseEntity.ok().body(new AuthLogoutResponse("로그아웃이 완료 되었습니다."));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/withdrawal")
    public void delete(@Valid @RequestBody AuthDeleteRequest deleteRequest) {
        signUpService.delete(deleteRequest);
    }

}

