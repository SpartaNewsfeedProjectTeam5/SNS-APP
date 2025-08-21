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

/**
 * 게시물 관련 요청을 처리하는 컨트롤러
 *
 * <p>요청을 받아 게시물 생성, 조회, 검색, 수정, 삭제 기능 제공</p>
 */
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    /**
     * 새로운 게시물 생성
     *
     * @param postBaseRequest 게시물 기본 요청 DTO
     * @param request         HTTP 요청 정보
     * @return HTTP 상태 코드와 일정 기본 응답 DTO
     */
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostBaseResponse> create(@Valid @RequestBody PostBaseRequest postBaseRequest,
                                                   HttpServletRequest request) {
        // 로그인 유저 이메일 확인
        String loginUserEmail = SessionUtils.getLoginUserEmailByServlet(request).orElseThrow(
                () -> new CustomException(ErrorCode.NEED_AUTH)
        );

        return new ResponseEntity<>(postService.create(loginUserEmail, postBaseRequest), HttpStatus.CREATED);
    }

    /**
     * 게시물 검색
     *
     * @param keyword    검색 키워드
     * @param searchType 검색 타입
     * @param pageable   페이지, 사이즈, 정렬, 정렬방향을 받는 {@link Pageable} 객체.
     * @return HTTP 상태 코드와 게시물 페이지 응답 DTO의 List
     */
    @GetMapping("/api/v1/posts/search")
    public ResponseEntity<List<PostPageResponse>> search(@RequestParam String keyword,
                                                         @RequestParam SearchType searchType,
                                                         @PageableDefault(
                                                                 page = 0,
                                                                 size = 10,
                                                                 sort = "createAt",
                                                                 direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostPageResponse> postPageResponse = postService.search(keyword, searchType, pageable);

        return new ResponseEntity<>(postPageResponse.getContent(), HttpStatus.OK);
    }

}
