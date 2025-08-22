package org.example.snsapp.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.follow.service.FollowDomainService;
import org.example.snsapp.domain.user.dto.*;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.domain.user.repository.UserRepository;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
import org.example.snsapp.global.util.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FollowDomainService followDomainService;

    @Transactional(readOnly = true)
    public UserBaseResponse getUserProfile(String email) {
        User user = userRepository.findByEmailOrElseThrow(email);

        int followerCount = followDomainService.getFollowerCount(user.getId());
        int followingCount = followDomainService.getFollowingCount(user.getId());

        return UserBaseResponse.create(user, followerCount, followingCount);
    }

    @Transactional
    public UserUpdateResponse updateUserProfile(String email, UserUpdateRequest dto) {
        User user = userRepository.findByEmailOrElseThrow(email);

        user.updateUserProfile(dto);

        // 수정 후 DTO를 바로 반환하기 위해 강제로 DB에 반영
        // @LastModifiedDate 같은 Auditing 필드가 DTO에 반영되도록 flush 필요
        userRepository.saveAndFlush(user);

        return UserUpdateResponse.create(user);
    }

    @Transactional
    public UserPasswordResponse updatePassword(String email, UserPasswordRequest dto) {
        User user = userRepository.findByEmailOrElseThrow(email);

        if (ObjectUtils.nullSafeEquals(dto.getCurrentPassword(), dto.getNewPassword())) {
            throw new CustomException(ErrorCode.CURRENT_PASSWORD_SAME);
        }

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.CURRENT_PASSWORD_NOT_MATCH);
        }

        String encodePassword = passwordEncoder.encode(dto.getNewPassword());
        user.updatePassword(encodePassword);
        // 수정 후 DTO를 바로 반환하기 위해 강제로 DB에 반영
        userRepository.saveAndFlush(user);

        return UserPasswordResponse.create("비밀번호가 성공적으로 변경 되었습니다.", user.getModifiedAt());
    }
}
