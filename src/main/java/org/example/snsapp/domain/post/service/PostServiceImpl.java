package org.example.snsapp.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.post.dto.PostBaseRequest;
import org.example.snsapp.domain.post.dto.PostBaseResponse;
import org.example.snsapp.domain.post.dto.PostPageResponse;
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

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

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

    @Override
    public Page<PostPageResponse> search(String keyword, SearchType searchType, Pageable pageable) {
        Page<Post> postPage = postRepository.search(keyword, searchType.toString(), pageable);

        return postPage.map(post -> {
            int commentCount = 1;
            int likeCount = 1;
            return PostPageResponse.create(post, commentCount, likeCount);
        });
    }
}
