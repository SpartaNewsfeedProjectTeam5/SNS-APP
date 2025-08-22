package org.example.snsapp.domain.follow.repository;

import org.example.snsapp.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
