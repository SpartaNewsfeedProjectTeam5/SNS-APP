package org.example.snsapp.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.follow.repository.FollowRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowDomainService {

    private final FollowRepository followRepository;

    public int getFollowerCount(Long userId) {
        return followRepository.countByFollowingId(userId);
    }

    public int getFollowingCount(Long userId) {
        return followRepository.countByFollowerId(userId);
    }
}
