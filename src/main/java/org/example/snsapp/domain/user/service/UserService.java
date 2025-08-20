package org.example.snsapp.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.user.dto.UserBaseResponse;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserBaseResponse getUserProfile(String email) {
        User user = userRepository.findByEmailOrElseThrow(email);

        return UserBaseResponse.create(user, 0, 0);
    }
}
