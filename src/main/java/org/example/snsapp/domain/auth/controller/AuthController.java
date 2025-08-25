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
@RequestMapping("/api")
public class AuthController {

    private final AuthService signUpService;

    @PostMapping("/v1/auth/login")
    public ResponseEntity<AuthLoginResponse> signIn(@Valid @RequestBody AuthLoginRequest loginRequest, HttpServletRequest request){
        AuthLoginResponse auth = signUpService.login(loginRequest);
        request.getSession().setAttribute(Const.LOGIN_USER, auth.getEmail());
        return ResponseEntity.ok(auth);
    }

    @PostMapping("/v1/auth/signup")
    public ResponseEntity<AuthSignUpResponse> signUp (@Valid @RequestBody AuthSignUpRequest authRequest) {
        return ResponseEntity.ok(signUpService.signUp(authRequest));
    }

    @PostMapping("/v1/auth/logout")
    public ResponseEntity<AuthLogoutResponse> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return ResponseEntity.ok(new AuthLogoutResponse());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/v1/auth/withdrawal")
    public ResponseEntity<AuthDeleteResponse> delete(@Valid @RequestBody AuthDeleteRequest deleteRequest) {
        return ResponseEntity.ok(signUpService.delete(deleteRequest));
    }

}

