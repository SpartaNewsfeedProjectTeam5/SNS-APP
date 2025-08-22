package org.example.snsapp.domain.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.post.dto.PostRequest;
import org.example.snsapp.domain.post.dto.PostResponse;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    /**
     * 새로운 게시물 생성
     *
     * @param postRequest 게시물 기본 요청 DTO
     * @param request     HTTP 요청 정보
     * @return HTTP 상태 코드와 게시물 기본 응답 DTO
     */
    @PostMapping("/v1/posts")
    public ResponseEntity<PostResponse> create(
            @Valid @RequestBody PostRequest postRequest,
            HttpServletRequest request) {
        // 로그인 유저 이메일 확인
        String loginUserEmail = SessionUtils.getLoginUserEmailByServlet(request).orElseThrow(
                () -> new CustomException(ErrorCode.NEED_AUTH)
        );

        return new ResponseEntity<>(postService.create(loginUserEmail, postRequest), HttpStatus.CREATED);
    }

    /**
     * 게시물 검색
     *
     * @param keyword    검색 키워드
     * @param searchType 검색 타입
     * @param pageable   페이지, 사이즈, 정렬, 정렬방향을 받는 {@link Pageable} 객체.
     * @return HTTP 상태 코드와 게시물 페이지 응답 DTO의 List
     */
    @GetMapping("/v1/posts/search")
    public ResponseEntity<List<PostResponse>> search(
            @RequestParam String keyword,
            @RequestParam SearchType searchType,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponse> postResponse = postService.search(keyword, searchType, pageable);

        return new ResponseEntity<>(postResponse.getContent(), HttpStatus.OK);
    }

    /**
     * 로그인 유저 게시물 조회
     *
     * @param pageable 페이지, 사이즈, 정렬, 정렬방향을 받는 {@link Pageable} 객체.
     * @param request  HTTP 요청 정보
     * @return HTTP 상태 코드와 게시물 페이지 응답 DTO의 List
     */
    @GetMapping("/v1/posts/myposts")
    public ResponseEntity<List<PostResponse>> findAllBySessionEmail(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable
            , HttpServletRequest request) {
        String loginUserEmail = getSessionEmail(request);

        Page<PostResponse> postResponse = postService.findAllByEmail(loginUserEmail, pageable);

        return new ResponseEntity<>(postResponse.getContent(), HttpStatus.OK);
    }

    /**
     * 게시물 수정
     *
     * @param postId      게시물 아이디
     * @param postRequest 게시물 수정 요청 DTO
     * @param request     HTTP 요청 정보
     * @return HTTP 상태 코드와 게시물 기본 응답 DTO
     */
    @PutMapping("/v1/posts/{postId}")
    public ResponseEntity<PostResponse> update(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequest postRequest,
            HttpServletRequest request) {
        String loginUserEmail = getSessionEmail(request);

        PostResponse postResponse = postService.update(postId, loginUserEmail, postRequest);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    /**
     * 게시물 삭제
     *
     * @param postId  게시물 아이디
     * @param request HTTP 요청 정보
     * @return HTTP 상태 코드
     */
    @DeleteMapping("/v1/posts/{postId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId,
                                       HttpServletRequest request) {
        String loginUserEmail = getSessionEmail(request);

        postService.delete(postId, loginUserEmail);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 게시물 좋아요 생성
     *
     * @param postId  게시물 아이디
     * @param request HTTP 요청 정보
     * @return HTTP 상태 코드와 게시물 기본 응답 DTO
     */
    @PostMapping("/v1/posts/{postId}/likes")
    public ResponseEntity<PostResponse> addLike(
            @PathVariable Long postId,
            HttpServletRequest request) {
        String loginUserEmail = getSessionEmail(request);

        return ResponseEntity.ok(postService.addLike(postId, loginUserEmail));
    }

    /**
     * 게시물 좋아요 삭제
     *
     * @param postId  게시물 아이디
     * @param request HTTP 요청 정보
     * @return HTTP 상태 코드와 게시물 기본 응답 DTO
     */
    @DeleteMapping("/v1/posts/{postId}/likes")
    public ResponseEntity<Void> removeLike(
            @PathVariable Long postId,
            HttpServletRequest request) {
        String loginUserEmail = getSessionEmail(request);
        postService.removeLike(postId, loginUserEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * HTTPServlet 으로부터 이메일을 받아온다.
     *
     * @param request HTTP 요청 정보
     * @return 이메일
     */
    String getSessionEmail(HttpServletRequest request) {
        return SessionUtils.getLoginUserEmailByServlet(request).
                orElseThrow(() -> new CustomException(ErrorCode.NEED_AUTH));
    }
}
