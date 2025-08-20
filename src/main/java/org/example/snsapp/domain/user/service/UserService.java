package org.example.snsapp.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.user.dto.UserBaseResponse;
import org.example.snsapp.domain.user.dto.UserUpdateRequest;
import org.example.snsapp.domain.user.dto.UserUpdateResponse;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.domain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    public UserUpdateResponse updateUserProfile(UserUpdateRequest dto) {
        //  TODO: 세션 미구현으로 임시 하드코딩 값 사용
        Long userId = 3L; // 임시 하드코딩 (테스트용)

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        user.updateUserProfile(dto);

        // 수정 후 DTO를 바로 반환하기 위해 강제로 DB에 반영
        // @LastModifiedDate 같은 Auditing 필드가 DTO에 반영되도록 flush 필요
        userRepository.saveAndFlush(user);

        // TODO: followerCount, followingCount는 팔로우 기능 구현 후 실제 값으로 변경
        return UserUpdateResponse.create(user);
    }
}
