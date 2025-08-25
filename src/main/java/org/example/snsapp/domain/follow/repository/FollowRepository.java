package org.example.snsapp.domain.follow.repository;

import org.example.snsapp.domain.follow.dto.FollowerDto;
import org.example.snsapp.domain.follow.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

    @Query("""
        SELECT new org.example.snsapp.domain.follow.dto.FollowerDto(u.email, u.username)
        FROM Follow f
        JOIN f.follower u
        WHERE f.following.id = :userId
    """)
    Page<FollowerDto> findFollowerByUserId(
            @Param("userId") Long userId,
            Pageable pageable
    );

    List<Follow> findAllByFollowerId(Long followerId);

    int countByFollowingId(Long userId);

    int countByFollowerId(Long userId);
}
