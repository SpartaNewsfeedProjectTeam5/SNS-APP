package org.example.snsapp.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.like.service.LikeService;
import org.example.snsapp.domain.post.dto.PostRequest;
import org.example.snsapp.domain.post.dto.PostResponse;
import org.example.snsapp.domain.post.entity.Post;
import org.example.snsapp.domain.post.repository.PostRepository;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.domain.user.repository.UserRepository;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.enums.LikeContentType;
import org.example.snsapp.global.enums.SearchType;
import org.example.snsapp.global.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * {@link PostService}의 구현체
 *
 * <p>JPA를 사용하여 데이터를 데이터베이스에 저장, 조회, 수정, 삭제하는 기능 제공</p>
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final LikeService likeService;
    private final UserRepository userRepository;


    @Transactional
    @Override
    public PostResponse create(String loginUserEmail, PostRequest postRequest) {
        User user = userRepository.findByEmailOrElseThrow(loginUserEmail);

        Post post = Post.builder()
                .user(user)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();

        Post savedPost = postRepository.save(post);

        return PostResponse.create(savedPost);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostResponse> search(String keyword, SearchType searchType, Pageable pageable) {
        Page<Post> postPage = postRepository.search(keyword, searchType.toString(), pageable);

        return postPage.map(PostResponse::create);
    }


    @Transactional(readOnly = true)
    @Override
    public Page<PostResponse> findAllByEmail(String loginUserEmail, Pageable pageable) {
        Page<Post> postPage = postRepository.findAllByUserEmailOrderByCreatedAtDesc(loginUserEmail, pageable);

        return postPage.map(PostResponse::create);
    }

    @Transactional
    @Override
    public PostResponse update(Long postId, String loginUserEmail, PostRequest postRequest) {
        Post post = postRepository.findPostByIdOrThrow(postId);

        // 로그인 유저와 게시물 유저 확인
        if (!MatchAuthorEmail(post, loginUserEmail))
            throw new CustomException(ErrorCode.NO_PERMISSION);

        post.update(
                Optional.ofNullable(postRequest.getTitle()).orElse(post.getTitle()),
                Optional.ofNullable(postRequest.getContent()).orElse(post.getContent())
        );

        return PostResponse.create(post);
    }

    @Transactional
    @Override
    public void delete(Long postId, String loginUserEmail) {
        Post post = postRepository.findPostByIdOrThrow(postId);

        // 로그인 유저와 게시물 유저 확인
        if (!MatchAuthorEmail(post, loginUserEmail))
            throw new CustomException(ErrorCode.NO_PERMISSION);

        postRepository.delete(post);
    }

    @Transactional
    @Override
    public PostResponse addLike(Long postId, String loginUserEmail) {
        Post post = postRepository.findPostByIdOrThrow(postId);

        // 작성자와 로그인 유저가 같다면 좋아요 금지
        if (MatchAuthorEmail(post, loginUserEmail))
            throw new CustomException(ErrorCode.POST_LIKE_PERMISSION_ERROR);

        User user = userRepository.findByEmailOrElseThrow(loginUserEmail);

        likeService.addLike(user, LikeContentType.POST, postId);
        post.increaseLikeCount();

        return PostResponse.create(post);
    }

    @Transactional
    @Override
    public void removeLike(Long postId, String loginUserEmail) {
        Post post = postRepository.findPostByIdOrThrow(postId);

        // 작성자와 로그인 유저가 같다면 좋아요 금지
        if (MatchAuthorEmail(post, loginUserEmail))
            throw new CustomException(ErrorCode.POST_LIKE_PERMISSION_ERROR);

        User user = userRepository.findByEmailOrElseThrow(loginUserEmail);

        likeService.removeLike(user, LikeContentType.POST, postId);
        post.decreaseLikeCount();
    }

    /**
     * 작성자 이메일과 {@code email}을 비교해 반환
     *
     * @param post  게시물 엔티티
     * @param email 이메일
     * @return 같다면 true, 틀리다면 false
     */
    private boolean MatchAuthorEmail(Post post, String email) {
        return Objects.equals(post.getUser().getEmail(), email);
    }

    /**
     * 게시글 ID로 게시글 조회
     */
    @Transactional(readOnly = true)
    @Override
    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }
}
