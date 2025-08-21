package org.example.snsapp.domain.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.post.dto.PostBaseRequest;
import org.example.snsapp.domain.post.dto.PostBaseResponse;
import org.example.snsapp.domain.post.dto.PostPageResponse;
import org.example.snsapp.domain.post.service.PostService;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.enums.SearchType;
import org.example.snsapp.global.exception.CustomException;
import org.example.snsapp.global.util.SessionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/api/v1/posts/search")
    public ResponseEntity<List<PostPageResponse>> search(@RequestParam String keyword,
                                                         @RequestParam SearchType searchType,
                                                         @PageableDefault(
                                                                 page = 0,
                                                                 size = 10,
                                                                 sort = "create_at",
                                                                 direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostPageResponse> postPageResponse = postService.search(keyword, searchType, pageable);

        return new ResponseEntity<>(postPageResponse.getContent(), HttpStatus.OK);
    }

}
