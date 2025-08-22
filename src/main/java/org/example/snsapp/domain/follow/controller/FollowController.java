package org.example.snsapp.domain.follow.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.follow.dto.FollowActionResponse;
import org.example.snsapp.domain.follow.dto.FollowRequest;
import org.example.snsapp.domain.follow.dto.FollowerDto;
import org.example.snsapp.domain.follow.dto.FollowerResponse;
import org.example.snsapp.domain.follow.service.FollowService;
import org.example.snsapp.global.constant.Const;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @DeleteMapping("/v1/follows/unfollow")
    public ResponseEntity<FollowActionResponse> unfollow(
            @SessionAttribute(name = Const.LOGIN_USER, required = false) String email,
            @Valid @RequestBody FollowRequest dto
    ) {
        if (email == null) throw new CustomException(ErrorCode.NEED_AUTH);

        return ResponseEntity.ok(followService.unfollow(email, dto));
    }

    @GetMapping("/v1/follows/followers")
    public ResponseEntity<FollowerResponse> findFollowers(
            @SessionAttribute(name = Const.LOGIN_USER, required = false) String email,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        if (email == null) throw new CustomException(ErrorCode.NEED_AUTH);

        return ResponseEntity.ok(followService.findFollowers(email, pageable));
    }
}
