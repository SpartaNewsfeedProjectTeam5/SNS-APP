package org.example.snsapp.domain.follow.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.follow.dto.FollowActionResponse;
import org.example.snsapp.domain.follow.dto.FollowRequest;
import org.example.snsapp.domain.follow.service.FollowService;
import org.example.snsapp.global.constant.Const;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/v1/follows")
    public ResponseEntity<FollowActionResponse> follow(
            @SessionAttribute(name = Const.LOGIN_USER, required = false) String email,
            @Valid @RequestBody FollowRequest dto
    ) {
        if (email == null) throw new CustomException(ErrorCode.NEED_AUTH);

        return ResponseEntity.ok(followService.follow(email, dto));
    }
}
