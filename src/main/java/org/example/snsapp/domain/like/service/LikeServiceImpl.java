package org.example.snsapp.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.like.dto.LikeResponse;
import org.example.snsapp.domain.like.entity.Like;
import org.example.snsapp.domain.like.repository.LikeRepository;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.enums.LikeContentType;
import org.example.snsapp.global.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<LikeResponse> findAllLikeByUser(User user) {

        List<Like> likes = likeRepository.findLikesByUser(user);
        return likes.stream().map(LikeResponse::create).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addLike(User user, LikeContentType type, Long typeId) {
        // 중복 검사
        if (existsByUserAndTypeAndTypeId(user, type, typeId))
            throw new CustomException(ErrorCode.ALREADY_LIKED);

        Like like = Like.builder()
                .user(user)
                .type(type)
                .typeId(typeId)
                .build();

        likeRepository.save(like);
    }

    @Transactional
    @Override
    public void removeLike(User user, LikeContentType type, Long typeId) {
        Like like = likeRepository.findByUserAndTypeAndTypeId(user, type, typeId).orElseThrow(
                () -> new CustomException(ErrorCode.LIKE_NOT_FOUND));

        likeRepository.delete(like);
    }


    @Transactional(readOnly = true)
    @Override
    public boolean existsByUserAndTypeAndTypeId(User user, LikeContentType type, Long typeId) {
        return likeRepository.existsByUserAndTypeAndTypeId(user, type, typeId);
    }
}
