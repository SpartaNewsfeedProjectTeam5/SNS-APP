package org.example.snsapp.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.follow.service.FollowService;
import org.example.snsapp.domain.like.service.LikeService;
import org.example.snsapp.domain.notification.service.NotificationService;
import org.example.snsapp.domain.post.dto.PostRequest;
import org.example.snsapp.domain.post.dto.PostResponse;
import org.example.snsapp.domain.post.entity.Post;
import org.example.snsapp.domain.post.repository.PostRepository;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.domain.user.service.UserDomainService;
import org.example.snsapp.domain.user.service.UserService;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.enums.LikeContentType;
import org.example.snsapp.global.enums.NotificationContentType;
import org.example.snsapp.global.enums.SearchType;
import org.example.snsapp.global.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final UserDomainService userDomainService;
    private final NotificationService notificationService;
    private final FollowService followService;

    @Transactional
    @Override
    public PostResponse create(String loginUserEmail, PostRequest postRequest) {
        User user = userDomainService.getUserByEmail(loginUserEmail);

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

    @Transactional(readOnly = true)
    @Override
    public Page<PostResponse> findAllByFollowingUser(String loginUserEmail, Pageable pageable) {
        Long userId = userDomainService.getUserByEmail(loginUserEmail).getId();
        List<User> followingUsers = followService.getFollowingUsersByUserId(userId);
        Page<Post> postPage = postRepository.findByUserIn(followingUsers, pageable);

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

        User user = userDomainService.getUserByEmail(loginUserEmail);

        likeService.addLike(user, LikeContentType.POST, postId);
        post.increaseLikeCount();

        // 알람 생성
        createPostLikeNotification(user, post);

        return PostResponse.create(post);
    }

    @Transactional
    @Override
    public void removeLike(Long postId, String loginUserEmail) {
        Post post = postRepository.findPostByIdOrThrow(postId);

        // 작성자와 로그인 유저가 같다면 좋아요 금지
        if (MatchAuthorEmail(post, loginUserEmail))
            throw new CustomException(ErrorCode.POST_LIKE_PERMISSION_ERROR);

        User user = userDomainService.getUserByEmail(loginUserEmail);

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
     * 게시글 좋아요 알람 생성
     *
     * @param from 보내는 유저
     * @param post 게시글 엔티티
     */
    private void createPostLikeNotification(User from, Post post) {
        String message = from.getUsername() + "님이 " + post.getTitle() + " 게시글에 좋아요를 남기셨습니다.";

        notificationService.create(
                from,
                post.getUser(),
                NotificationContentType.LIKE,
                post.getId(),
                message
        );
    }
}
