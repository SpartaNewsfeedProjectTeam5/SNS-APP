package org.example.snsapp.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
}
