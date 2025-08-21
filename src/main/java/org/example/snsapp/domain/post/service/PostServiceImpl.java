package org.example.snsapp.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.post.dto.PostBaseRequest;
import org.example.snsapp.domain.post.dto.PostBaseResponse;
import org.example.snsapp.domain.post.dto.PostPageResponse;
import org.example.snsapp.domain.post.dto.PostUpdateRequest;
import org.example.snsapp.domain.post.entity.Post;
import org.example.snsapp.domain.post.repository.PostRepository;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.domain.user.repository.UserRepository;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.enums.SearchType;
import org.example.snsapp.global.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@link PostService}의 구현체
 *
 * <p>JPA를 사용하여 데이터를 데이터베이스에 저장, 조회, 수정, 삭제하는 기능 제공</p>
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Transactional
    @Override
    public PostBaseResponse create(String loginUserEmail, PostBaseRequest postBaseRequest) {
        User user = userRepository.findUserByEmail(loginUserEmail).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Post post = Post.builder()
                .user(user)
                .title(postBaseRequest.getTitle())
                .content(postBaseRequest.getContent())
                .build();

        Post savedPost = postRepository.save(post);

        return PostBaseResponse.create(savedPost);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostPageResponse> search(String keyword, SearchType searchType, Pageable pageable) {
        Page<Post> postPage = postRepository.search(keyword, searchType.toString(), pageable);

        return postPage.map(post -> {
            int commentCount = 1;
            int likeCount = 1;
            return PostPageResponse.create(post, commentCount, likeCount);
        });
    }


    @Transactional(readOnly = true)
    @Override
    public Page<PostPageResponse> findAllByEmail(String loginUserEmail, Pageable pageable) {
        Page<Post> postPage = postRepository.findAllByUserEmailOrderByCreatedAtDesc(loginUserEmail, pageable);

        return postPage.map(post -> {
            int commentCount = 1;
            int likeCount = 1;
            return PostPageResponse.create(post, commentCount, likeCount);
        });
    }

    @Transactional
    @Override
    public PostBaseResponse update(Long postId, String loginUserEmail, PostUpdateRequest postUpdateRequest) {
        Post post = findPostByIdOrThrow(postId);

        // 로그인 유저와 게시물 유저 확인
        if (!matchesAuthorEmail(post, loginUserEmail))
            throw new CustomException(ErrorCode.NO_PERMISSION);

        post.update(
                Optional.ofNullable(postUpdateRequest.getTitle()).orElse(post.getTitle()),
                Optional.ofNullable(postUpdateRequest.getContent()).orElse(post.getContent())
        );

        return PostBaseResponse.create(post);
    }

    @Transactional
    @Override
    public void delete(Long postId, String loginUserEmail) {
        Post post = findPostByIdOrThrow(postId);

        // 로그인 유저와 게시물 유저 확인
        if (!matchesAuthorEmail(post, loginUserEmail))
            throw new CustomException(ErrorCode.NO_PERMISSION);

        postRepository.delete(post);
    }

    /**
     * 게시물 아이디로 게시물을 조회
     *
     * @param postId 게시물 아이디
     * @return 게시물 엔티티
     */
    @Transactional(readOnly = true)
    Post findPostByIdOrThrow(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );
    }

    /**
     * 게시물 작성자 이메일과 {@code email}를 비교해 반환
     *
     * @param post  게시물 엔티티
     * @param email 이메일
     * @return 같다면 true, 틀리다면 false
     */
    private boolean matchesAuthorEmail(Post post, String email) {
        return Objects.equals(post.getUser().getEmail(), email);
    }
}
