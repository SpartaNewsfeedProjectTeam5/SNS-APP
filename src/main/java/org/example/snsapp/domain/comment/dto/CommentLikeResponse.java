package org.example.snsapp.domain.comment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeResponse {

    private String message;

    @Builder
    public CommentLikeResponse(String message) {
        this.message = message;
    }

    public static CommentLikeResponse likeCreated() {
        return CommentLikeResponse.builder()
                .message("댓글 좋아요가 등록되었습니다.")
                .build();
    }

    public static CommentLikeResponse likeRemoved() {
        return CommentLikeResponse.builder()
                .message("댓글 좋아요가 취소되었습니다.")
                .build();
    }

}
