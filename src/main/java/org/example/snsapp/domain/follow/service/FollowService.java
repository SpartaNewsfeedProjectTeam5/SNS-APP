package org.example.snsapp.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.follow.dto.*;
import org.example.snsapp.domain.follow.entity.Follow;
import org.example.snsapp.domain.follow.repository.FollowRepository;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.domain.user.service.UserDomainService;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserDomainService userDomainService;

    @Transactional
    public FollowActionResponse follow(String email, FollowRequest dto) {
        FollowIds followIds = validateFollowAction(email, dto.getTargetUserEmail(), true);

        // 엔티티 조회 (연관관계 설정용)
        User followerUser = userDomainService.getUserById(followIds.getFollowerId());
        User followingUser = userDomainService.getUserById(followIds.getFollowingId());

        // 팔로우 생성 및 저장
        Follow follow = Follow.create(followerUser, followingUser);
        followRepository.save(follow);

        return FollowActionResponse.create("팔로우가 완료 되었습니다.", follow.getCreatedAt());
    }

    @Transactional
    public FollowActionResponse unfollow(String email, FollowRequest dto) {
        FollowIds followIds = validateFollowAction(email, dto.getTargetUserEmail(), false);

        followRepository.deleteByFollowerIdAndFollowingId(followIds.getFollowerId(), followIds.getFollowingId());

        return FollowActionResponse.ofUnfollow("언팔로우가 완료되었습니다");
    }

    @Transactional(readOnly = true)
    public FollowerResponse findFollowers(String email, Pageable pageable) {
        Long userId = userDomainService.getUserIdByEmail(email).getId();

        Page<FollowerDto> followPage = followRepository.findFollowerByUserId(userId, pageable);

        return FollowerResponse.create(followPage);
    }

    private FollowIds validateFollowAction(String loginEmail, String targetEmail, boolean isFollow) {
        // 자기 자신 팔로우/언팔로우 방지
        if (ObjectUtils.nullSafeEquals(loginEmail, targetEmail) && isFollow) throw new CustomException(ErrorCode.CANNOT_FOLLOW_SELF);
        if (ObjectUtils.nullSafeEquals(loginEmail, targetEmail) && !isFollow) throw new CustomException(ErrorCode.CANNOT_UNFOLLOW_SELF);

        // 팔로워(User)와 팔로잉(User) ID 조회 (DTO 사용)
        Long followerId = userDomainService.getUserIdByEmail(loginEmail).getId();
        Long followingId = userDomainService.getUserIdByEmail(targetEmail).getId();

        // 팔로잉 관계 여부
        boolean alreadyFollowing = followRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
        // 팔로우 시 이미 팔로잉이면 예외
        if (isFollow && alreadyFollowing) throw new CustomException(ErrorCode.ALREADY_FOLLOWING);
        // 언팔로우 시 팔로우가 없으면 예외
        if (!isFollow && !alreadyFollowing) throw new CustomException(ErrorCode.NOT_FOLLOWING);


        return FollowIds.create(followerId, followingId);
    }
}
