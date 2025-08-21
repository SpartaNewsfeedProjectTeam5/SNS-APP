package org.example.snsapp.domain.like.repository;

import org.example.snsapp.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

// 좋아요 레포지토리
public interface LikeRepository extends JpaRepository<Like, Long> {
}
