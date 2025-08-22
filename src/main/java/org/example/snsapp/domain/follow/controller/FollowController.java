package org.example.snsapp.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.follow.service.FollowService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
}
