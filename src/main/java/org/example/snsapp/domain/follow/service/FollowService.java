package org.example.snsapp.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.follow.dto.FollowActionResponse;
import org.example.snsapp.domain.follow.dto.FollowRequest;
import org.example.snsapp.domain.follow.entity.Follow;
import org.example.snsapp.domain.follow.repository.FollowRepository;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.domain.user.service.UserDomainService;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
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
        // 자기 자신 팔로우 방지
        if (ObjectUtils.nullSafeEquals(email, dto.getTargetUserEmail())) {
            throw new CustomException(ErrorCode.CANNOT_FOLLOW_SELF);
        }

        // 팔로워(User)와 팔로잉(User) ID 조회 (DTO 사용)
        Long followerId = userDomainService.getUserIdByEmail(email).getId();
        Long followingId = userDomainService.getUserIdByEmail(dto.getTargetUserEmail()).getId();

        // 중복 팔로우 체크
        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new CustomException(ErrorCode.ALREADY_FOLLOWING);
        }

        // 엔티티 조회 (연관관계 설정용)
        User followerUser = userDomainService.getUserById(followerId);
        User followingUser = userDomainService.getUserById(followingId);

        // 팔로우 생성 및 저장
        Follow follow = Follow.create(followerUser, followingUser);
        followRepository.save(follow);

        return FollowActionResponse.create("팔로우가 완료 되었습니다.", follow.getCreatedAt());
    }
}
