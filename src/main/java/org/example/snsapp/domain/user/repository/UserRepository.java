package org.example.snsapp.domain.user.repository;

import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    default User findUserByEmailOrElseThrow(String email) {
        return findUserByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }
}
