package org.example.snsapp.domain.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.user.dto.*;
import org.example.snsapp.domain.user.service.UserService;
import org.example.snsapp.global.constant.Const;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/v1/users/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @RequestParam
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            @Size(max = 40, message = "40자 이내로 입력해주세요.") String email
    ) {
        return ResponseEntity.ok(userService.getUserProfile(email));
    }

    @PutMapping("/v1/users/me/profile")
    public ResponseEntity<UserProfileResponse> updateUserProfile(
            @SessionAttribute(name = Const.LOGIN_USER, required = false) String email,
            @Valid @RequestBody UserUpdateRequest dto
    ) {
        if (email == null) throw new CustomException(ErrorCode.NEED_AUTH);

        return ResponseEntity.ok(userService.updateUserProfile(email, dto));
    }

    @PutMapping("/v1/users/me/password")
    public ResponseEntity<UserPasswordResponse> updatePassword(
            @SessionAttribute(name = Const.LOGIN_USER, required = false) String email,
            @Valid @RequestBody UserPasswordRequest dto
    ) {
        if (email == null) throw new CustomException(ErrorCode.NEED_AUTH);

        return ResponseEntity.ok(userService.updatePassword(email, dto));
    }
}
