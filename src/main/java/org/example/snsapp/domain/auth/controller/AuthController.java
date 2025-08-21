package org.example.snsapp.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.auth.dto.*;
import org.example.snsapp.domain.auth.service.AuthService;
import org.example.snsapp.global.constant.Const;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService signUpService;

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponse> signIn(@Valid @RequestBody AuthLoginRequest loginRequest, HttpServletRequest request) {
        AuthLoginResponse authLoginResponse = signUpService.login(loginRequest, request);
        String email = authLoginResponse.getEmail();

        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER, email);
        return ResponseEntity.ok().body(authLoginResponse);
    }

    @PostMapping("/signUp")
    public AuthSignUpResponse signUp(@Valid @RequestBody AuthSignUpRequest authRequest) {
        return signUpService.signUp(authRequest);
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

