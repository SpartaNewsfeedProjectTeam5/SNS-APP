package org.example.snsapp.domain.auth.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.auth.dto.*;
import org.example.snsapp.domain.auth.repository.AuthRepository;
import org.example.snsapp.domain.user.repository.UserRepository;
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
    public String login(AuthLoginRequest loginRequest) { //로그인
        User user = authRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));
        Boolean isResign = authRepository.userIsResignTrue(loginRequest.getEmail());
        if (isResign) {
            throw new IllegalStateException("탈퇴한 회원입니다.");
        }
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return user.getEmail();
        }
        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    } //삭제 유무 판단

    @Transactional //회원가입
    public AuthSignUpResponse signUp(AuthSignUpRequest signUpRequest) {
        Optional<User> optionalUser = authRepository.findByEmail(signUpRequest.getEmail());
        if (optionalUser.isPresent()) throw new EntityNotFoundException("이미 존재하는 사용자 아이디 입니다.");
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
        User user = authRepository.findByEmail(deleteRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));

        if (authRepository.userIsResignTrue(deleteRequest.getEmail())) {
            throw new IllegalStateException("이미 탈퇴한 회원입니다.");
        }
        if (passwordEncoder.matches(deleteRequest.getPassword(), user.getPassword())) {
            authRepository.setTrueUserIsResign(user.getId());
            return new AuthDeleteResponse();
        }
        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    //에러 반환 enum 으로 처리
}