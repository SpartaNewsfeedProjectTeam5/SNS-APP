package org.example.snsapp.domain.like.service;

import org.example.snsapp.domain.like.dto.LikeResponse;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.global.enums.LikeContentType;

import java.util.List;

public interface LikeService {

    List<LikeResponse> findAllLikeByUser(User user);

    /**
     * 좋아요 생성
     *
     * @param user   유저 엔티티
     * @param type   타입
     * @param typeId 타입 아이디
     */
    void addLike(User user, LikeContentType type, Long typeId);

    /**
     * 좋아요 제거
     *
     * @param user   유저 엔티티
     * @param type   타입
     * @param typeId 타입 아이디
     */
    void removeLike(User user, LikeContentType type, Long typeId);
}
