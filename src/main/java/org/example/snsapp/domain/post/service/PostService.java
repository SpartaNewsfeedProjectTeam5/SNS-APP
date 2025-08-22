package org.example.snsapp.domain.post.service;

import jakarta.validation.Valid;
import org.example.snsapp.domain.post.dto.PostRequest;
import org.example.snsapp.domain.post.dto.PostResponse;
import org.example.snsapp.domain.post.entity.Post;
import org.example.snsapp.global.enums.SearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 게시물 관력 로직을 처리하는 서비스 인터페이스
 *
 * <p>게시물 생성, 조회, 검색, 수정, 삭제 기능 정의</p>
 */
public interface PostService {
    /**
     * 새로운 게시물 생성
     *
     * @param loginUserEmail 유저 이메일
     * @param postRequest    게시물 기본 요청 DTO
     * @return 게시물 기본 응답 DTO
     */
    PostResponse create(String loginUserEmail, PostRequest postRequest);

    /**
     * 게시물 검색
     *
     * @param keyword    검색 키워드
     * @param searchType 검색 타입
     * @param pageable   페이지, 사이즈, 정렬, 정렬방향을 받는 {@link Pageable} 객체
     * @return 게시물 페이지 응답 DTO의 Page
     */
    Page<PostResponse> search(String keyword, SearchType searchType, Pageable pageable);

    /**
     * 로그인 유저 게시물 전체 조회
     *
     * @param loginUserEmail 로그인 유저 이메일
     * @param pageable       페이지, 사이즈를 받는 {@link Pageable} 객체
     * @return 게시물 페이지 응답 DTO의 Page
     */
    Page<PostResponse> findAllByEmail(String loginUserEmail, Pageable pageable);

    /**
     * 게시물 수정
     *
     * @param postId         게시물 아이디
     * @param loginUserEmail 로그인 유저 이메일
     * @param postRequest    게시물 수정 요청 DTO
     * @return 게시물 페이지 응답 DTO
     */
    PostResponse update(Long postId, String loginUserEmail, @Valid PostRequest postRequest);

    /**
     * 게시물 삭제
     *
     * @param postId         게시물 아이디
     * @param loginUserEmail 로그인 유저 이메일
     */
    void delete(Long postId, String loginUserEmail);

    PostResponse addLike(Long postId, String loginUserEmail);

    void removeLike(Long postId, String loginUserEmail);
}
