package org.example.snsapp.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.user.dto.UserBaseResponse;
import org.example.snsapp.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/newsfeeds/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/users/{email}/profile")
    public ResponseEntity<UserBaseResponse> getUserProfile(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserProfile(email));
    }
}
