package org.example.snsapp.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.user.dto.UserBaseResponse;
import org.example.snsapp.domain.user.dto.UserUpdateRequest;
import org.example.snsapp.domain.user.dto.UserUpdateResponse;
import org.example.snsapp.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/newsfeeds/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{email}/profile")
    public ResponseEntity<UserBaseResponse> getUserProfile(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserProfile(email));
    }

    @PutMapping("/users/me/profile")
    public ResponseEntity<UserUpdateResponse> updateUserProfile(@Valid @RequestBody UserUpdateRequest dto) {
        //  TODO: 인증 정보로 로그인 사용자 ID 가져오기
        return ResponseEntity.ok(userService.updateUserProfile(dto));
    }
}
