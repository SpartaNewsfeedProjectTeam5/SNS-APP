package org.example.snsapp.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.user.dto.UserBaseResponse;
import org.example.snsapp.domain.user.dto.UserUpdateRequest;
import org.example.snsapp.domain.user.dto.UserUpdateResponse;
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

        // TODO: followerCount, followingCount는 팔로우 기능 구현 후 실제 값으로 변경
        return UserBaseResponse.create(user, 0, 0);
    }

    @Transactional
    public UserUpdateResponse updateUserProfile(String email, UserUpdateRequest dto) {
        User user = userRepository.findByEmailOrElseThrow(email);

        user.updateUserProfile(dto);

        // 수정 후 DTO를 바로 반환하기 위해 강제로 DB에 반영
        // @LastModifiedDate 같은 Auditing 필드가 DTO에 반영되도록 flush 필요
        userRepository.saveAndFlush(user);

        // TODO: followerCount, followingCount는 팔로우 기능 구현 후 실제 값으로 변경
        return UserUpdateResponse.create(user);
    }
}
