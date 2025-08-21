package org.example.snsapp.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.example.snsapp.domain.user.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AuthRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    @Modifying
    @Query("UPDATE User u SET u.isResign = true WHERE u.id = :id")
    void setTrueUserIsResign(Long id);
    @Query("SELECT u.isResign FROM User u WHERE u.email = :email")
    Boolean userIsResignTrue(String email);
}
