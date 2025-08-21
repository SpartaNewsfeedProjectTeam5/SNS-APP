package org.example.snsapp.domain.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.post.dto.PostBaseRequest;
import org.example.snsapp.domain.post.dto.PostBaseResponse;
import org.example.snsapp.domain.post.entity.Post;
import org.example.snsapp.domain.post.service.PostService;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
import org.example.snsapp.global.util.SessionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostBaseResponse> create(@Valid @RequestBody PostBaseRequest postBaseRequest,
                                                   HttpServletRequest request) {
        String loginUserEmail = SessionUtils.getLoginUserEmailByServlet(request).orElseThrow(
                () -> new CustomException(ErrorCode.NEED_AUTH)
        );

        return new ResponseEntity<>(postService.create(loginUserEmail, postBaseRequest), HttpStatus.CREATED);
    }
}
