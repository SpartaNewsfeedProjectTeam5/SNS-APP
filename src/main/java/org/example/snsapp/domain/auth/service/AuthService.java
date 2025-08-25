package org.example.snsapp.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.auth.dto.*;
import org.example.snsapp.domain.auth.repository.AuthRepository;
import org.example.snsapp.domain.user.repository.UserRepository;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
import org.example.snsapp.global.util.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.snsapp.domain.user.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public AuthLoginResponse login(AuthLoginRequest loginRequest) { //로그인
        User user = authRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if (user.isResign()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new AuthLoginResponse(user.getEmail());
        }
        throw new CustomException(ErrorCode.CURRENT_PASSWORD_NOT_MATCH);
    }

    @Transactional //회원가입
    public AuthSignUpResponse signUp(AuthSignUpRequest signUpRequest) {
        Optional<User> optionalUser = authRepository.findByEmail(signUpRequest.getEmail());
        if (optionalUser.isPresent()) throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        User user = User.create(
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getUsername(),
                signUpRequest.getAge(),
                false,
                signUpRequest.getProfileImage()
        );
        userRepository.save(user);
        return AuthSignUpResponse.create(user);
    }

    @Transactional //회원탈퇴
    public AuthDeleteResponse delete(AuthDeleteRequest deleteRequest) {
        User user = authRepository.findByEmail(deleteRequest.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (user.isResign()) {
            throw new CustomException(ErrorCode.DELETED_USER);
        }
        if (passwordEncoder.matches(deleteRequest.getPassword(), user.getPassword())) {
            authRepository.setTrueUserIsResign(user.getId());
            return new AuthDeleteResponse();
        }
        throw new CustomException(ErrorCode.CURRENT_PASSWORD_NOT_MATCH);
    }
}
