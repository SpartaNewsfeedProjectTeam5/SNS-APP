package org.example.snsapp.domain.like.repository;

import org.example.snsapp.domain.like.entity.Like;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.global.enums.LikeContentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// 좋아요 레포지토리
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndTypeAndTypeId(User user, LikeContentType type, Long typeId);

    void deleteByUserAndTypeAndTypeId(User user, LikeContentType type, Long typeId);

    boolean existsByUserAndTypeAndTypeId(User user, LikeContentType type, Long typeId);

    List<Like> findLikesByUser(User user);

}
