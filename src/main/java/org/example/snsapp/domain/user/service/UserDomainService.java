package org.example.snsapp.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.user.dto.UserIdDto;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDomainService {

    private final UserRepository userRepository;

    /**
     * 이메일로 유저 ID 조회
     */
    public UserIdDto getUserIdByEmail(String email) {
        User user = userRepository.findByEmailOrElseThrow(email);
        return UserIdDto.create(user.getId());
    }

    /**
     * 연관관계 설정을 위한 엔티티 반환
     */
    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }

    /**
     * 이메일로 유저 엔티티 조회
     *
     * @param email 이메일
     * @return 유저 엔티티
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmailOrElseThrow(email);
    }

}
