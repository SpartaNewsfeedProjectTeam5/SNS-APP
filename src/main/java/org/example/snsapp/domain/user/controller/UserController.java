package org.example.snsapp.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/newsfeeds/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

}
