package org.example.snsapp.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.follow.repository.FollowRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
}
