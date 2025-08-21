package org.example.snsapp.domain.post.service;

import jakarta.validation.Valid;
import org.example.snsapp.domain.post.dto.PostBaseRequest;
import org.example.snsapp.domain.post.dto.PostBaseResponse;
import org.example.snsapp.domain.post.dto.PostPageResponse;
import org.example.snsapp.global.enums.SearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;

import java.net.URI;

/**
 * 게시물 관력 로직을 처리하는 서비스 인터페이스
 *
 * <p>게시물 생성, 조회, 검색, 수정, 삭제 기능 정의</p>
 */
public interface PostService {
    /**
     * 새로운 게시물 생성
     *
     * @param loginUserEmail  유저 이메일
     * @param postBaseRequest 게시물 기본 요청 DTO
     * @return 게시물 기본 응답 DTO
     */
    PostBaseResponse create(String loginUserEmail, PostBaseRequest postBaseRequest);

    /**
     * 게시물 검색
     *
     * @param keyword    검색 키워드
     * @param searchType 검색 타입
     * @param pageable   페이지, 사이즈, 정렬, 정렬방향을 받는 {@link Pageable} 객체.
     * @return 게시물 페이지 응답 DTO의 Page
     */
    Page<PostPageResponse> search(String keyword, SearchType searchType, Pageable pageable);
}
