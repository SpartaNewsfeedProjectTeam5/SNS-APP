package org.example.snsapp.domain.auth.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.auth.dto.*;
import org.example.snsapp.domain.auth.repository.AuthRepository;
import org.example.snsapp.domain.user.repository.UserRepository;
import org.example.snsapp.global.constant.Const;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
import org.example.snsapp.global.util.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public AuthLoginResponse login(AuthLoginRequest loginRequest, HttpServletRequest request) { //로그인

        User user = authRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("없는 회원입니다."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new CustomException(ErrorCode.AUTH_ERROR);


        return new AuthLoginResponse(user.getEmail());
    }

    @Transactional //회원가입
    public AuthSignUpResponse signUp(AuthSignUpRequest signUpRequest) {
        Optional<User> optionalUser = authRepository.findByEmail(signUpRequest.getEmail());
        if (optionalUser.isPresent()) throw new EntityNotFoundException("이미 존재하는 사용자 아이디 입니다.");
        User user = userRepository.save(new User(signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getName(), signUpRequest.getAge(), false, signUpRequest.getProfileImage()));
        return AuthSignUpResponse.create(user);
    }

    @Transactional //회원탈퇴
    public ResponseEntity delete(AuthDeleteRequest deleteRequest) {
        if ((authRepository.findByEmail(deleteRequest.getEmail())).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이미 탈퇴했거나 존재하지 않는 회원 입니다.");
        }
        User user = authRepository.findByEmail(deleteRequest.getEmail()).orElseThrow(EntityNotFoundException::new);
        if (passwordEncoder.matches(deleteRequest.getPassword(), user.getPassword())) {
            authRepository.setTrueUserIsResign(user.getId());
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
    }
}
